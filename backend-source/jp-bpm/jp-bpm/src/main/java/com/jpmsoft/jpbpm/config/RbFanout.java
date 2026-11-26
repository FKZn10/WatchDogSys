package com.jpmsoft.jpbpm.config;

import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.FanoutInfoDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/25 11:32
 */
@Slf4j
@Component
public class RbFanout {

    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SysConfigEntityService sysConfigEntityService;
    private static final String exchange = "fanout_status_exchange";
    private static final String routingKey = "";


    public void fanout(Object obj) {
        try {
            Object conf = redisUtils.get("Config_fanoutMsg2Rabbit");
            if (conf == null) {
                conf = sysConfigEntityService.getConfByCode("fanoutMsg2Rabbit");
                conf = conf == null ? "0" : conf;
                redisUtils.set("Config_fanoutMsg2Rabbit", conf);
            }
            if (conf.equals("1")) {
                log.info("fanout_status_exchange send!");
                amqpTemplate.convertAndSend(exchange, routingKey, JacksonUtils.toJSONString(obj));
            }
        } catch (Exception ex) {
            log.error("Rabbit Fanout error! message:" + ex.getMessage(),ex);
            ex.printStackTrace();
        }
    }

    public void fanout(String table, String primaryKey, String stateKey, String timeKey, String id, Integer state, Date time) {
        if (!Objects.equals(state, ApprovalStatusEnum.Already_Over.getIndex())) return;
        FanoutInfoDTO info = new FanoutInfoDTO();
        info.setTable(table);
        info.setPrimaryKey(primaryKey);
        info.setStateKey(stateKey);
        info.setTimeKey(timeKey);
        info.setId(id);
        info.setState(state);
        info.setTime(time);
        fanout(info);
    }
}
