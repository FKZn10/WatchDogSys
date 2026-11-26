const { createHmac, randomBytes, randomUUID, timingSafeEqual } = require('node:crypto');

const HEADER = Buffer.from(JSON.stringify({ alg: 'HS256', typ: 'JWT' })).toString('base64url');
const ISSUER = 'wuhan-monitor-local';
const AUDIENCE = 'wuhan-monitor-ui';
const MAX_AGE = 12 * 60 * 60;

function createSessions({ key = randomBytes(32), now = () => Math.floor(Date.now() / 1000) } = {}) {
  const sessions = new Map();
  const sign = data => createHmac('sha256', key).update(data).digest('base64url');

  function issue(upstreamToken) {
    const iat = now();
    const exp = iat + MAX_AGE;
    for (const [id, session] of sessions) if (session.exp <= iat) sessions.delete(id);
    const jti = randomUUID();
    const payload = Buffer.from(JSON.stringify({ iss: ISSUER, aud: AUDIENCE, jti, iat, exp })).toString('base64url');
    const input = `${HEADER}.${payload}`;
    sessions.set(jti, { upstreamToken, exp });
    return `${input}.${sign(input)}`;
  }

  function resolve(jwt) {
    if (typeof jwt !== 'string' || jwt.length > 2048) return null;
    const parts = jwt.split('.');
    if (parts.length !== 3 || parts[0] !== HEADER || !parts.slice(1).every(p => /^[\w-]+$/.test(p))) return null;
    const [header, payload, signature] = parts;
    const expected = Buffer.from(sign(`${header}.${payload}`));
    const supplied = Buffer.from(signature);
    if (supplied.length !== expected.length || !timingSafeEqual(supplied, expected)) return null;
    try {
      const claims = JSON.parse(Buffer.from(payload, 'base64url').toString());
      const session = sessions.get(claims.jti);
      if (claims.iss !== ISSUER || claims.aud !== AUDIENCE || claims.exp <= now() || session?.exp !== claims.exp) return null;
      return { id: claims.jti, token: session.upstreamToken };
    } catch { return null; }
  }

  return { issue, resolve, revoke: id => sessions.delete(id) };
}

module.exports = { createSessions };
