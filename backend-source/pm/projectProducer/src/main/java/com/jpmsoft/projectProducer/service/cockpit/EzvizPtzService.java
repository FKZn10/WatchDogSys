package com.jpmsoft.projectProducer.service.cockpit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraPtzRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.LongSupplier;

/** Single Producer instance coordinator. No global/multi-instance lock is implied. */
@Service
public class EzvizPtzService {
    private static final Logger LOG=LoggerFactory.getLogger(EzvizPtzService.class);
    static final long TTL=4000, MAX_HOLD=60000;
    private final EzvizPlaybackService credentials;
    private final RestTemplate http;
    private final LongSupplier nanos;
    private final boolean enabled;
    private final Map<String,Slot> slots=new HashMap<>();
    private final ScheduledThreadPoolExecutor timers=new ScheduledThreadPoolExecutor(4, task->{
        Thread t=new Thread(task,"ezviz-ptz-stop");t.setDaemon(true);return t;
    });
    private final ObjectMapper json=new ObjectMapper();
    private volatile boolean closed;
    private static final Map<String,Integer> DIRECTIONS=new HashMap<>();
    static {DIRECTIONS.put("UP",0);DIRECTIONS.put("DOWN",1);DIRECTIONS.put("LEFT",2);DIRECTIONS.put("RIGHT",3);
        DIRECTIONS.put("ZOOM_IN",8);DIRECTIONS.put("ZOOM_OUT",9);}

    @Autowired public EzvizPtzService(EzvizPlaybackService credentials,@Value("${ezviz.ptz.enabled:false}") boolean enabled) {
        this(credentials,client(),enabled,System::nanoTime);
    }
    EzvizPtzService(EzvizPlaybackService credentials,RestTemplate http,boolean enabled,LongSupplier nanos) {
        this.credentials=credentials;this.http=http;this.enabled=enabled;this.nanos=nanos;
        timers.setRemoveOnCancelPolicy(true);
    }
    public static class Failure extends RuntimeException {
        private final int code;
        private Map<String,Object> details;
        public Failure(int code,String message){super(message);this.code=code;}
        public int getCode(){return code;}
        public Map<String,Object> getDetails(){return details;}
    }
    private static Failure fail(int code,String message){return new Failure(code,message);}
    private String[] binding(SmartSiteCamera c) {
        try {
            String[] b=credentials.statusBinding(c);
            int channel=Integer.parseInt(b[1]);
            if(channel<1||channel>65535)throw new IllegalArgumentException();
            return new String[]{b[0].toUpperCase(Locale.ROOT),Integer.toString(channel)};
        } catch(IllegalArgumentException e){throw fail(422,"萤石设备类型、地址或通道绑定配置不正确");}
    }
    private synchronized Slot slot(String[] b) {
        if(closed)throw fail(503,"云台服务正在关闭");
        String key=b[0]+":"+b[1];
        Slot s=slots.get(key);
        if(s==null){if(slots.size()>=256)throw fail(503,"云台协调通道数量已达上限");s=new Slot();slots.put(key,s);}
        return s;
    }
    public Map<String,Object> capabilities(SmartSiteCamera camera) {
        String[] b=binding(camera);Slot s=slot(b);
        synchronized(s){
            Map<String,Boolean> flags=capacity(s,camera,b);
            Map<String,Object> out=new LinkedHashMap<>();out.put("sscId",camera.getSscId());out.put("ptz",flags);return out;
        }
    }
    private Map<String,Boolean> capacity(Slot s,SmartSiteCamera c,String[] b) {
        if(s.capabilities!=null&&nanos.getAsLong()<s.capabilityDeadline)return s.capabilities;
        JsonNode data=vendor("capacity",c,b,null,null).path("data");
        if(!data.isObject())throw fail(502,"萤石云台能力查询失败");
        Map<String,Boolean> flags=new LinkedHashMap<>();
        flags.put("vertical","1".equals(data.path("ptz_top_bottom").asText()));
        flags.put("horizontal","1".equals(data.path("ptz_left_right").asText()));
        flags.put("zoom","1".equals(data.path("ptz_zoom").asText()));
        s.capabilities=flags;s.capabilityDeadline=nanos.getAsLong()+TimeUnit.SECONDS.toNanos(60);return flags;
    }
    public Map<String,Object> control(SmartSiteCamera c,CameraPtzRequest r,String actor) {
        if(!enabled||closed)throw fail(503,"萤石云台控制未启用或服务正在关闭");
        if(actor==null||actor.trim().isEmpty())throw fail(403,"缺少平台操作身份");
        String action=r.getAction();
        if(!Arrays.asList("START","STOP","RENEW").contains(action))throw fail(400,"云台动作必须为 START、RENEW 或 STOP");
        if("START".equals(action)&&(!DIRECTIONS.containsKey(r.getCommand())||r.getSpeed()==null||r.getSpeed()<1||r.getSpeed()>2))
            throw fail(400,"命令仅支持上下左右与变倍，海康速度须为 1 或 2");
        String[] b=binding(c);Slot s=slot(b);
        synchronized(s){
            if(closed)throw fail(503,"云台服务正在关闭");
            Lease lease=s.lease;
            if("START".equals(action)) {
                if(lease!=null&&nanos.getAsLong()>=lease.deadline&&!lease.quarantined)stop(s,lease,"EXPIRED");
                if(s.lease!=null)throw fail(409,"该通道正在被控制或停止尚未确认");
                Map<String,Boolean> caps=capacity(s,c,b);
                String cap=r.getCommand().startsWith("ZOOM")?"zoom":Arrays.asList("UP","DOWN").contains(r.getCommand())?"vertical":"horizontal";
                if(!Boolean.TRUE.equals(caps.get(cap)))throw fail(422,"该通道不支持此云台动作");
                lease=new Lease(actor,c,b,r.getCommand(),DIRECTIONS.get(r.getCommand()),nanos.getAsLong());
                s.lease=lease;schedule(s,lease);audit(lease,"START","REQUESTED");
                // A timeout can mean START reached the device. Always attempt STOP on failure.
                try {vendor("ptz/start",c,b,lease.direction,r.getSpeed());}
                catch(Failure e){stop(s,lease,"START_UNCERTAIN");if(s.lease!=null)e.details=view(lease,"STOP_UNCERTAIN");throw e;}
                if(nanos.getAsLong()>=lease.deadline){stop(s,lease,"START_EXPIRED");Failure e=fail(409,"启动响应过晚，已尝试停止，请重新操作");if(s.lease!=null)e.details=view(lease,"STOP_UNCERTAIN");throw e;}
                audit(lease,"START","COMMAND_ACCEPTED");return view(lease,"COMMAND_ACCEPTED");
            }
            if(lease==null||!lease.id.equals(r.getLeaseId())||!lease.actor.equals(actor)||!Objects.equals(lease.camera.getTeId(),c.getTeId()))
                throw fail(409,"控制租约不存在、已释放或不属于当前操作人");
            if("STOP".equals(action)) {
                if(!stop(s,lease,"EXPLICIT"))throw fail(502,"停止请求未确认，通道保持占用，请重试停止");
                return view(lease,"STOP_ACCEPTED");
            }
            if(lease.quarantined||nanos.getAsLong()>=lease.deadline){if(!lease.quarantined)stop(s,lease,"EXPIRED");throw fail(409,"租约已到期或停止尚未确认");}
            lease.deadline=Math.min(nanos.getAsLong()+TimeUnit.MILLISECONDS.toNanos(TTL),lease.hardDeadline);
            schedule(s,lease);return view(lease,"LEASE_RENEWED");
        }
    }
    private Map<String,Object> view(Lease l,String status) {
        Map<String,Object> result=new LinkedHashMap<>();result.put("status",status);result.put("leaseId",l.id);
        result.put("renewAfterMs",1000);result.put("remainingMs",Math.max(0,TimeUnit.NANOSECONDS.toMillis(l.deadline-nanos.getAsLong())));return result;
    }
    private void schedule(Slot s,Lease l) {
        if(l.timer!=null)l.timer.cancel(false);
        l.timer=timers.schedule(()->{synchronized(s){if(s.lease==l&&!l.quarantined){
            if(nanos.getAsLong()>=l.deadline)stop(s,l,"WATCHDOG");else schedule(s,l);
        }}},Math.max(1,l.deadline-nanos.getAsLong()),TimeUnit.NANOSECONDS);
    }
    private boolean stop(Slot s,Lease l,String reason) {
        if(s.lease!=l)return true;
        boolean accepted=false;
        for(int attempt=0;attempt<3&&!accepted;attempt++){
            try {vendor("ptz/stop",l.camera,l.binding,l.direction,null);accepted=true;}catch(RuntimeException ignored){}
        }
        if(l.timer!=null)l.timer.cancel(false);
        if(accepted)s.lease=null;else l.quarantined=true;
        audit(l,"STOP_"+reason,accepted?"STOP_ACCEPTED":"STOP_UNCERTAIN");return accepted;
    }
    private JsonNode vendor(String operation,SmartSiteCamera camera,String[] b,Integer direction,Integer speed) {
        try {
            MultiValueMap<String,String> form=new LinkedMultiValueMap<>();
            form.add("accessToken",credentials.statusAccessToken(camera));form.add("deviceSerial",b[0]);form.add("channelNo",b[1]);
            if(direction!=null)form.add("direction",direction.toString());if(speed!=null)form.add("speed",speed.toString());
            JsonNode response=json.readTree(http.postForObject("https://open.ys7.com/api/lapp/device/"+operation,form,String.class));
            String code=response==null?"":response.path("code").asText();
            if("200".equals(code))return response;
            if("10002".equals(code)){credentials.invalidateAccessToken(camera);throw fail(502,"萤石凭证已失效，已清除缓存，请重试");}
            if("60000".equals(code)||"60020".equals(code))throw fail(422,"设备不支持此云台操作");
            if("60001".equals(code))throw fail(403,"萤石账号没有该通道的云台权限");
            if("20007".equals(code))throw fail(409,"设备离线");
            if(Arrays.asList("60002","60003","60004","60005").contains(code))throw fail(409,"云台已到达限位");
            throw fail(502,"萤石云台请求未成功，请检查权限、凭证或设备网络");
        } catch(Failure e){throw e;}catch(Exception e){throw fail(502,"萤石云台请求超时或响应异常，执行结果需核对");}
    }
    private void audit(Lease l,String action,String result) {
        LOG.info("ezvizPtz actor={} camera={} action={} command={} result={}",l.actor,l.camera.getSscId(),action,l.command,result);
    }
    private static RestTemplate client(){SimpleClientHttpRequestFactory f=new SimpleClientHttpRequestFactory();f.setConnectTimeout(1500);f.setReadTimeout(2000);return new RestTemplate(f);}
    static class Slot {Lease lease;Map<String,Boolean> capabilities;long capabilityDeadline;}
    static class Lease {
        final String id=UUID.randomUUID().toString(),actor,command;final SmartSiteCamera camera;final String[] binding;final int direction;
        final long hardDeadline;long deadline;boolean quarantined;ScheduledFuture<?> timer;
        Lease(String actor,SmartSiteCamera camera,String[] binding,String command,int direction,long now){this.actor=actor;this.camera=camera;this.binding=binding;this.command=command;this.direction=direction;hardDeadline=now+TimeUnit.MILLISECONDS.toNanos(MAX_HOLD);deadline=now+TimeUnit.MILLISECONDS.toNanos(TTL);}
    }
    @PreDestroy public void close(){closed=true;List<Slot> all; synchronized(this){all=new ArrayList<>(slots.values());}
        for(Slot s:all)synchronized(s){if(s.lease!=null)stop(s,s.lease,"SHUTDOWN");}timers.shutdownNow();}
}
