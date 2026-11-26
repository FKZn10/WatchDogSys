package com.jpmsoft.videoaccess.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "video")
public class VideoProperties {
    private boolean demoEnabled;
    private String demoProjectId = "demo-project";
    private CompanyDirectoryProperties companyDirectory = new CompanyDirectoryProperties();
    private ModuleProperties module = new ModuleProperties();
    public ModuleProperties getModule() { return module; }
    public void setModule(ModuleProperties module) { this.module = module; }

    public boolean isDemoEnabled() { return demoEnabled; }
    public void setDemoEnabled(boolean demoEnabled) { this.demoEnabled = demoEnabled; }
    public String getDemoProjectId() { return demoProjectId; }
    public void setDemoProjectId(String demoProjectId) { this.demoProjectId = demoProjectId; }
    public CompanyDirectoryProperties getCompanyDirectory() { return companyDirectory; }
    public void setCompanyDirectory(CompanyDirectoryProperties companyDirectory) { this.companyDirectory = companyDirectory; }
    public String getMode() {
        return module.isEnabled() ? "PLATFORM_MODULE" : demoEnabled ? "LOCAL_DEMO" : companyDirectory.isEnabled() ? "COMPANY_DIRECTORY" : "DISCONNECTED";
    }
}
