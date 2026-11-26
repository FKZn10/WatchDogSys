package com.jpmsoft.videoaccess.service;

import com.jpmsoft.videoaccess.adapter.company.CompanyCameraClient;
import com.jpmsoft.videoaccess.config.VideoProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.CameraQuery;
import com.jpmsoft.videoaccess.model.VideoModels.CameraPage;
import com.jpmsoft.videoaccess.port.VideoPorts.CameraCatalog;
import com.jpmsoft.videoaccess.port.VideoPorts.ProjectAccess;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CameraQueryService {
    private final VideoProperties properties;
    private final CompanyCameraClient company;
    private final ProjectAccess access;
    private final CameraCatalog catalog;

    public CameraQueryService(VideoProperties properties, CompanyCameraClient company,
                              ProjectAccess access, CameraCatalog catalog) {
        this.properties = properties; this.company = company; this.access = access; this.catalog = catalog;
    }
    public CameraPage query(CameraQuery request) {
        if (properties.getCompanyDirectory().isEnabled()) {
            ServletRequestAttributes context = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String token = context == null ? null : context.getRequest().getHeader("token");
            // Every call goes through PM's authenticated, tenant-aware, data-filtered LIST endpoint.
            // An empty authorized list does not establish permission to control that whole project.
            return company.query(request, token);
        }
        access.requireIdentity();
        access.requireProject(request.getpId());
        return catalog.query(request.getpId(), request.getPage(), request.getSize());
    }
}
