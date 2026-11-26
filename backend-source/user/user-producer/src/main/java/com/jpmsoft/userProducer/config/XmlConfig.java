package com.jpmsoft.userProducer.config;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class XmlConfig {
    private static boolean historySearchEnabled = false;

    public static boolean hsEnable() {
        return historySearchEnabled;
    }

    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    public void historySearchEnabled(Boolean bool) {
        historySearchEnabled = bool;
    }

    public void historySearchEnabled() {
        String enable = sysConfigEntityService.getSysConfigByCode("projectCompanyHistoryNameSearch");
        if (ValidateUtil.isEmpty(enable)) {
            historySearchEnabled = false;
        } else if (enable.equals("1")) {
            historySearchEnabled = true;
        } else
            historySearchEnabled = false;
    }

}
