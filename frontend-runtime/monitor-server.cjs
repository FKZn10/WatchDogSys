const http = require('http');
const fs = require('fs');
const path = require('path');
const { createSessions } = require('./monitor-jwt.cjs');
const root = path.join(__dirname, 'frontend');
const types = {'.html':'text/html; charset=utf-8','.js':'application/javascript','.css':'text/css','.png':'image/png','.svg':'image/svg+xml','.jpg':'image/jpeg','.ico':'image/x-icon','.wasm':'application/wasm','.json':'application/json','.mp4':'video/mp4'};

function createMonitorServer({ upstreamPort = 8085, sessions = createSessions() } = {}) {
return http.createServer((req,res) => {
  const url = new URL(req.url,'http://localhost');
  if (url.pathname.startsWith('/hbgpc/apis/')) {
    const route = url.pathname.slice('/hbgpc/apis'.length).toLowerCase();
    const login = req.method === 'POST' && route === '/api/v1/systemmanage/login';
    const logout = route === '/api/v1/systemmanage/loginout';
    const headers = {...req.headers,host:`127.0.0.1:${upstreamPort}`};
    let verifiedSession;
    if (typeof headers.token === 'string' && headers.token.includes('.')) {
      verifiedSession = sessions.resolve(headers.token);
      if (!verifiedSession) {
        res.writeHead(401, {'content-type':'application/json','cache-control':'no-store'});
        res.end(JSON.stringify({success:false,statusCode:401,message:'本机登录已失效，请重新登录。'}));
        return;
      }
      headers.token = verifiedSession.token;
    }
    if (login) headers['accept-encoding'] = 'identity';
    const proxy = http.request({host:'127.0.0.1',port:upstreamPort,path:req.url.slice('/hbgpc/apis'.length),method:req.method,headers},up => {
      if (logout && verifiedSession) sessions.revoke(verifiedSession.id);
      if (!login || up.statusCode !== 200) { res.writeHead(up.statusCode,up.headers);up.pipe(res);return; }
      const chunks = [];
      let size = 0;
      up.on('error',() => { if (!res.headersSent) res.writeHead(502);res.end(); });
      up.on('data',chunk => {
        size += chunk.length;
        if (size > 1024 * 1024) { res.writeHead(502);res.end();up.destroy();return; }
        chunks.push(chunk);
      });
      up.on('end',() => {
        if (res.writableEnded) return;
        const headers = {...up.headers,'cache-control':'no-store'};
        let body = Buffer.concat(chunks);
        try {
          const loginInfo = JSON.parse(body.toString('utf8'));
          if (loginInfo.success === true && typeof loginInfo.data?.token === 'string' && loginInfo.data.token) {
            loginInfo.data.token = sessions.issue(loginInfo.data.token);
            body = Buffer.from(JSON.stringify(loginInfo));
            delete headers['transfer-encoding'];
            delete headers['content-encoding'];
            delete headers.etag;
            headers['content-length'] = body.length;
          }
        } catch { /* Convert successful JSON login responses only. */ }
        res.writeHead(up.statusCode,headers);
        res.end(body);
      });
    });
    proxy.setTimeout(30000,()=>proxy.destroy());
    proxy.on('error',()=>{if(!res.headersSent)res.writeHead(502,{'content-type':'application/json'});res.end(JSON.stringify({success:false,message:'本地后端尚未就绪，请检查 logs 目录。'}));});
    req.pipe(proxy); return;
  }
  if(url.pathname==='/'){res.writeHead(302,{Location:'/hbgpc/video/'});res.end();return;}
  let relative;
  try { relative=decodeURIComponent(url.pathname.replace(/^\/hbgpc\/video\/?/,'')); } catch {res.writeHead(400);res.end();return;}
  let file=path.resolve(root,relative);
  if(!file.startsWith(root+path.sep)&&file!==root){res.writeHead(403);res.end();return;}
  if(!fs.existsSync(file)||fs.statSync(file).isDirectory()) {
    if(path.extname(relative)){res.writeHead(404);res.end();return;}
    file=path.join(root,'index.html');
  }
  const mime = types[path.extname(file)] || 'application/octet-stream';
  const size = fs.statSync(file).size;
  const headers = {'content-type':mime,'content-length':size,'cache-control':'no-cache'};
  const range = mime === 'video/mp4' && typeof req.headers.range === 'string' ? /^bytes=(\d+)-(\d*)$/.exec(req.headers.range) : null;
  if(mime === 'video/mp4') headers['accept-ranges'] = 'bytes';
  let bounds;
  if (range) {
    const start = Number(range[1]);
    const end = range[2] ? Math.min(Number(range[2]),size-1) : size-1;
    if (start >= size || start > end) { res.writeHead(416,{'content-range':`bytes */${size}`});res.end();return; }
    bounds = {start,end};
    headers['content-range'] = `bytes ${start}-${end}/${size}`;
    headers['content-length'] = end-start+1;
  }
  res.writeHead(bounds ? 206 : 200,headers);
  if(req.method === 'HEAD') {res.end();return;}
  fs.createReadStream(file,bounds).on('error',()=>res.destroy()).pipe(res);
});
}

if (require.main === module) createMonitorServer().listen(18080,'127.0.0.1',()=>console.log('http://127.0.0.1:18080/hbgpc/video/'));
module.exports = { createMonitorServer };
