package com.jpmsoft.projectProducer.service.cockpit;

import com.fasterxml.jackson.databind.*;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/** Local device/NVR recording only. No video bytes or persistent playback credentials. */
@Service
public class EzvizRecordingService {
    static final ZoneId ZONE=ZoneId.of("Asia/Shanghai");
    static final DateTimeFormatter FORM=DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
    static final DateTimeFormatter URL_TIME=DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    final EzvizPlaybackService credentials; final RestTemplate http; final boolean enabled;
    final ObjectMapper json=new ObjectMapper();
    @Autowired public EzvizRecordingService(EzvizPlaybackService c,@Value("${ezviz.recording.enabled:false}") boolean e){this(c,client(),e);}
    EzvizRecordingService(EzvizPlaybackService c,RestTemplate h,boolean e){credentials=c;http=h;enabled=e;}
    public static class Failure extends RuntimeException {
        final int code; public Failure(int c,String m){super(m);code=c;} public int getCode(){return code;}
    }
    static Failure fail(int c,String m){return new Failure(c,m);}
    static RestTemplate client(){SimpleClientHttpRequestFactory f=new SimpleClientHttpRequestFactory();f.setConnectTimeout(2000);f.setReadTimeout(5000);return new RestTemplate(f);}
    static String trim(String s){return s==null?"":s.trim();}
    static ZonedDateTime time(String s){
        try {return OffsetDateTime.parse(trim(s)).atZoneSameInstant(ZONE).withNano(0);}
        catch(DateTimeException ignored){try{return LocalDateTime.parse(trim(s),FORM).atZone(ZONE);}catch(DateTimeException e){throw fail(400,"时间需为带时区ISO格式或北京时间yyyy-MM-dd HH:mm:ss");}}
    }
    static class Range {ZonedDateTime start,end; Range(CameraRecordingRequest r){
        if(r==null)throw fail(400,"缺少回放请求");
        if(!trim(r.getRecordSource()).isEmpty()&&!"LOCAL".equals(r.getRecordSource()))throw fail(422,"当前只支持设备或NVR本地录像");
        start=time(r.getStartTime());end=time(r.getEndTime());
        if(!start.isBefore(end)||!start.toLocalDate().equals(end.toLocalDate()))throw fail(400,"起止时间需在同一北京时间自然日且开始早于结束");
    }}
    static class Page {String serial,channel;boolean fromNvr,more;long next;List<long[]> records=new ArrayList<>();}
    String[] binding(SmartSiteCamera c){
        if(!enabled)throw fail(503,"萤石回放入口未启用");
        try{String[] b=credentials.statusBinding(c);int channel=Integer.parseInt(b[1]);if(channel<1)throw new IllegalArgumentException();return new String[]{b[0].toUpperCase(Locale.ROOT),String.valueOf(channel)};}catch(IllegalArgumentException e){throw fail(422,"请核对萤石类型、直播编码及设备绑定");}
    }
    JsonNode decode(String body){
        try {JsonNode n=json.readTree(body);if(n==null||!n.isObject())throw fail(502,"萤石返回数据异常");return n;}
        catch(java.io.IOException|IllegalArgumentException e){throw fail(502,"萤石返回数据异常");}
    }
    void check(JsonNode n,boolean v3){
        String code=(v3?n.path("meta").path("code"):n.path("code")).asText();
        if("200".equals(code))return;
        if("10031".equals(code)||"20018".equals(code))throw fail(403,"萤石账号无权访问录像");
        if("20007".equals(code)||"2003".equals(code))throw fail(409,"录像设备离线");
        if("20015".equals(code)||"2030".equals(code))throw fail(422,"设备不支持录像查询或回放");
        throw fail(502,"萤石录像请求失败，请核对权限、设备与网络");
    }
    Page query(SmartSiteCamera camera,String[] source,Range range){
        for(int attempt=0;attempt<2;attempt++){
            HttpHeaders headers=new HttpHeaders();headers.set("accessToken",credentials.statusAccessToken(camera));headers.set("deviceSerial",source[0].toUpperCase(Locale.ROOT));headers.set("localIndex",source[1]);
            String url="https://open.ys7.com/api/v3/device/local/video/unify/query?startTime="+range.start.toEpochSecond()+"&endTime="+range.end.toEpochSecond()+"&pageSize=100&isQueryByNvr=1";
            JsonNode n;
            try{n=decode(http.exchange(url,HttpMethod.GET,new HttpEntity<Void>(headers),String.class).getBody());}
            catch(org.springframework.web.client.HttpStatusCodeException e){n=decode(e.getResponseBodyAsString());}
            catch(org.springframework.web.client.RestClientException e){throw fail(502,"萤石录像查询网络异常");}
            if("10002".equals(n.path("meta").path("code").asText())&&attempt==0){credentials.invalidateAccessToken(camera);continue;}
            check(n,true);JsonNode d=n.path("data");
            if(!d.isObject()||!d.path("records").isArray())throw fail(502,"萤石录像列表格式异常");
            Page p=new Page();p.fromNvr=d.path("fromNvr").asBoolean(false);
            p.serial=d.path("deviceSerial").asText(source[0]);p.channel=d.path("localIndex").asText(source[1]);
            if(!p.serial.matches("[A-Za-z0-9]+")||!p.channel.matches("[1-9][0-9]*"))throw fail(502,"萤石录像来源格式异常");
            if(!p.fromNvr&&(!p.serial.equalsIgnoreCase(source[0])||!p.channel.equals(source[1])))throw fail(502,"萤石返回录像来源不一致");
            p.more=d.path("hasMore").asBoolean(false);p.next=d.path("nextFileTime").asLong(0);
            if(p.more&&(p.next<=range.start.toEpochSecond()||p.next>=range.end.toEpochSecond()))throw fail(502,"萤石录像分页游标异常");
            for(JsonNode row:d.path("records")){
                long a=row.path("startTime").asLong(0),b=row.path("endTime").asLong(0);
                if(a<=0||b<=a)throw fail(502,"萤石录像时间异常");
                a=Math.max(a,range.start.toEpochSecond());b=Math.min(b,range.end.toEpochSecond());
                if(b>a)p.records.add(new long[]{a,b});
            }
            p.records.sort(Comparator.comparingLong(a->a[0]));return p;
        }
        throw fail(502,"萤石录像认证失败");
    }
    static String iso(long t){return Instant.ofEpochSecond(t).atZone(ZONE).toOffsetDateTime().toString();}
    /** Bounded, stateless calendar scan; clients merge pages before claiming a range is empty. */
    public Map<String,Object> calendar(SmartSiteCamera camera, com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordCalendarRequest request) {
        if(request==null)throw fail(400,"缺少录像日历请求");
        LocalDate first,last;
        try {first=LocalDate.parse(trim(request.getStartDate()));last=LocalDate.parse(trim(request.getEndDate()));}
        catch(DateTimeException e){throw fail(400,"日期需为yyyy-MM-dd");}
        long count=java.time.temporal.ChronoUnit.DAYS.between(first,last)+1;
        if(count<1||count>31)throw fail(400,"录像日历范围需为1至31个自然日");
        ZonedDateTime begin=first.atStartOfDay(ZONE),end=last.atTime(23,59,59).atZone(ZONE);
        ZonedDateTime cursor=trim(request.getNextStartTime()).isEmpty()?begin:time(request.getNextStartTime());
        if(cursor.isBefore(begin)||!cursor.isBefore(end))throw fail(400,"续查时间不在查询范围内");
        if(!trim(request.getRecordSource()).isEmpty()&&!"LOCAL".equals(request.getRecordSource()))throw fail(422,"当前只支持设备或NVR本地录像");
        String[] source=binding(camera);
        ZonedDateTime scanStart=cursor,scanEnd=cursor;
        Set<String> dates=new TreeSet<>(),completed=new TreeSet<>();
        Long earliest=null,latest=null;
        int queries=0;boolean complete=false;
        long deadline=System.nanoTime()+java.util.concurrent.TimeUnit.SECONDS.toNanos(8);
        while(queries<3&&(queries==0||System.nanoTime()<deadline)) {
            ZonedDateTime dayEnd=cursor.toLocalDate().atTime(23,59,59).atZone(ZONE);
            CameraRecordingRequest part=new CameraRecordingRequest();
            part.setStartTime(cursor.toOffsetDateTime().toString());part.setEndTime(dayEnd.toOffsetDateTime().toString());
            Page page=query(camera,source,new Range(part));queries++;
            for(long[] segment:page.records) {
                dates.add(cursor.toLocalDate().toString());
                earliest=earliest==null?segment[0]:Math.min(earliest,segment[0]);
                latest=latest==null?segment[1]:Math.max(latest,segment[1]);
            }
            if(page.more) {cursor=Instant.ofEpochSecond(page.next).atZone(ZONE);scanEnd=cursor;}
            else {
                completed.add(cursor.toLocalDate().toString());scanEnd=dayEnd;
                if(cursor.toLocalDate().equals(last)){complete=true;break;}
                cursor=cursor.toLocalDate().plusDays(1).atStartOfDay(ZONE);
            }
        }
        Map<String,Object> result=new LinkedHashMap<>();
        result.put("sscId",camera.getSscId());result.put("recordSource","LOCAL");result.put("timeZone",ZONE.getId());
        result.put("startDate",first.toString());result.put("endDate",last.toString());
        result.put("scannedStartTime",scanStart.toOffsetDateTime().toString());result.put("scannedEndTime",scanEnd.toOffsetDateTime().toString());
        result.put("availableDates",new ArrayList<>(dates));result.put("completedDates",new ArrayList<>(completed));
        result.put("earliestTime",earliest==null?null:iso(earliest));result.put("latestTime",latest==null?null:iso(latest));
        result.put("complete",complete);result.put("hasMore",!complete);result.put("nextStartTime",complete?null:cursor.toOffsetDateTime().toString());
        return result;
    }

    public Map<String,Object> records(SmartSiteCamera c,CameraRecordingRequest r){
        Range range=new Range(r);Page p=query(c,binding(c),range);
        Map<String,Object> out=new LinkedHashMap<>();out.put("sscId",c.getSscId());out.put("recordSource","LOCAL");out.put("timeZone",ZONE.getId());
        List<Map<String,Object>> rows=new ArrayList<>();for(long[] v:p.records){Map<String,Object> row=new LinkedHashMap<>();row.put("startTime",iso(v[0]));row.put("endTime",iso(v[1]));rows.add(row);}
        out.put("records",rows);out.put("hasMore",p.more);out.put("nextStartTime",p.more?iso(p.next):null);out.put("fromNvr",p.fromNvr);return out;
    }
    public Map<String,Object> playback(SmartSiteCamera c,CameraRecordingRequest r){
        Range range=new Range(r);Page p=query(c,binding(c),range);
        if(p.records.isEmpty())throw fail(p.more?409:404,p.more?"请从下一页录像范围选择回放时间":"所选时段没有录像");
        // Play only one confirmed contiguous interval; never pretend gaps are recorded.
        long start=p.records.get(0)[0],end=p.records.get(0)[1];
        for(int i=1;i<p.records.size();i++){long[] v=p.records.get(i);if(v[0]>end)break;end=Math.max(end,v[1]);}
        String begin=Instant.ofEpochSecond(start).atZone(ZONE).format(URL_TIME),stop=Instant.ofEpochSecond(end).atZone(ZONE).format(URL_TIME);
        for(int attempt=0;attempt<2;attempt++){
            Map<String,Object> credential=credentials.recordingToken(c);
            LinkedMultiValueMap<String,String> form=new LinkedMultiValueMap<>();form.add("accessToken",(String)credential.get("accessToken"));form.add("deviceSerial",p.serial);form.add("channelNo",p.channel);form.add("protocol","1");form.add("type","2");form.add("startTime",Instant.ofEpochSecond(start).atZone(ZONE).format(FORM));form.add("stopTime",Instant.ofEpochSecond(end).atZone(ZONE).format(FORM));
            JsonNode n;try{n=decode(http.postForObject("https://open.ys7.com/api/lapp/v2/live/address/get",form,String.class));}catch(org.springframework.web.client.RestClientException e){throw fail(502,"萤石回放取址网络异常");}
            if("10002".equals(n.path("code").asText())&&attempt==0){credentials.invalidateAccessToken(c);continue;}
            check(n,false);String address=n.path("data").path("url").asText("");
            try{
                URI u=new URI(address);String path=u.getPath();
                if(!"ezopen".equals(u.getScheme())||!"open.ys7.com".equals(u.getHost())||u.getRawUserInfo()!=null||u.getPort()!=-1||u.getFragment()!=null||path==null||!path.matches("/"+java.util.regex.Pattern.quote(p.serial)+"/"+java.util.regex.Pattern.quote(p.channel)+"(?:\\.hd)?(?:\\.local)?\\.rec"))throw fail(502,"萤石回放地址绑定异常");
                // SDK begin/end are canonical 14-digit device-local times.
                address="ezopen://open.ys7.com"+path+"?begin="+begin+"&end="+stop;
            }catch(URISyntaxException e){throw fail(502,"萤石回放地址格式异常");}
            Map<String,Object> out=new LinkedHashMap<>(credential);out.put("sscId",c.getSscId());out.put("recordSource","LOCAL");out.put("timeZone",ZONE.getId());out.put("ezopenUrl",address);out.put("startTime",iso(start));out.put("endTime",iso(end));out.put("fromNvr",p.fromNvr);return out;
        }
        throw fail(502,"萤石回放认证失败");
    }
}
