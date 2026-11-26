package com.jpmsoft.common.model.request.project;

import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDeviceCoordinateModel;

import java.io.Serializable;
import java.util.List;

public class PreProjectDeviceCoordinateCreateOrUpdateRequest implements Serializable {

    List<PreProjectDeviceCoordinateModel> preProjectDeviceCoordinateModelList;

    public List<PreProjectDeviceCoordinateModel> getPreProjectDeviceCoordinateModelList() {
        return preProjectDeviceCoordinateModelList;
    }

    public void setPreProjectDeviceCoordinateModelList(List<PreProjectDeviceCoordinateModel> preProjectDeviceCoordinateModelList) {
        this.preProjectDeviceCoordinateModelList = preProjectDeviceCoordinateModelList;
    }
}
