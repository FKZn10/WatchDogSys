package com.jpmsoft.fileUpload.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.GanttLinksDTO;
import com.jpmsoft.fileUpload.pojo.dto.GanttTasksDto;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.vo.GanttTasksMppVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于解析mpp文件
 *
 * @author Aaron
 * @date 2021/12/20 10:22
 */
@Slf4j
public class MpxjUtil {

    // NO.1 解析mpp文件,同时获取TaskInfo
    public static GanttTasksMppVo readInputStream(String filePath, List<SysDictionary> dictionaries) {
        try {
            //这个是读取文件的组件
            MPPReader mppRead = new MPPReader();
            //注意，如果在这一步出现了读取异常，肯定是版本不兼容，换个版本试试
            ProjectFile pf = mppRead.read(new File(filePath));
            //从文件中获取的任务对象
            List<Task> tasks = pf.getChildTasks();
            //这个可以不用，这个list只是我用来装下所有的数据，如果不需要可以不使用
            List<GanttTasksDto> proList = new ArrayList<>();
            //所有的ID index对应taskNumber
            //List<String> allIds = new ArrayList<>();
            Map<String, String> idMapping = new HashMap<>();
            List<GanttLinksDTO> links = new ArrayList<>();
            if (tasks != null && tasks.size() == 1) {
                idMapping.put(tasks.get(0).getID().toString(), GenerateUUID.getUUID());
                //根节点移除
                tasks = tasks.get(0).getChildTasks();
            }
            int sort = 1;
            for (Task task : tasks) {
                GanttTasksDto model = new GanttTasksDto();
                model.setTaskParent("0");
                int levelNum = 1;
                model.setTaskText(task.getName());
                if (ValidateUtil.isNotNull(task.getDuration())) {
                    String taskDuration = task.getDuration().toString().trim().replace("d", "").replace("?", "");
                    model.setTaskDuration(Float.valueOf(taskDuration));
                }
                model.setTaskOutlineLevel(levelNum + "");
                model.setTaskStartDate(task.getStart());
                model.setTaskEndDate(task.getFinish());
                model.setTaskSort(sort += 1);
                model.setTaskPriority("正常");
                model.setTaskId(GenerateUUID.getUUID());
                model.setTaskRemark(task.getNotes());
                model.setTaskNumber(task.getID().toString());
                model.setTaskState(1);
                if (task.getPredecessors() != null) {
                    List<Relation> data = task.getPredecessors();
                    if (!CollectionUtils.isEmpty(data)) {
                        String preNumber = "";
                        for (int i = 0; i < data.size(); i++) {
                            GanttLinksDTO link = new GanttLinksDTO();
                            link.setLinkId(GenerateUUID.getUUID());
                            //前台web解析线source targer与mpp的相反故此处反向赋值返回
                            link.setLinkSource(data.get(i).getTargetTask().getID() + "");
                            link.setLinkTarget(data.get(i).getSourceTask().getID() + "");
                            int finalI = i;
                            List<SysDictionary> types = dictionaries.stream().filter(n -> n.getDName().equalsIgnoreCase(data.get(finalI).getType().toString())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(types)) {
                                link.setLinkType(Integer.parseInt(types.stream().findFirst().get().getDCode()));
                                links.add(link);
                            }
                            preNumber += link.getLinkTarget();
                            if (i != data.size() - 1)
                                preNumber += ",";
                        }
                        model.setTaskPreTaskId(preNumber);
                    }
                }
                idMapping.put(task.getID().toString(), model.getTaskId());
                List<GanttTasksDto> child = new ArrayList<>();
                if (task.getChildTasks().size() > 0) {
                    getChildTasks(task.getChildTasks(), levelNum, child, model.getTaskId(), idMapping, dictionaries, links, sort);
                }
                model.setChildNodes(child);
                proList.add(model);
            }

            List<GanttTasksDto> list = new ArrayList<>();
            for (GanttTasksDto model : proList) {
                String preTaskId = "";
                if (!ValidateUtil.isEmpty(model.getTaskPreTaskId())) {
                    String[] sk = model.getTaskPreTaskId().split(",");
                    for (int i = 0; i < sk.length; i++) {
                        preTaskId += idMapping.get(sk[i]);
                        if (i != sk.length - 1)
                            preTaskId += ",";
                    }
                }
                model.setTaskPreTaskId(preTaskId);
                List<GanttTasksDto> child = new ArrayList<>();
                if (!CollectionUtils.isEmpty(model.getChildNodes())) {
                    setChildPreTaskId(model.getChildNodes(), child, idMapping);
                }
                model.setChildNodes(child);
                list.add(model);
            }

            List<GanttLinksDTO> retLinks = new ArrayList<>();
            for (GanttLinksDTO link : links) {
                link.setLinkTarget(idMapping.get(link.getLinkTarget()));
                link.setLinkSource(idMapping.get(link.getLinkSource()));
                retLinks.add(link);
            }
            GanttTasksMppVo vo = new GanttTasksMppVo();
            vo.setLinks(retLinks);
            vo.setTasks(list);
            return vo;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }


    /**
     * 替换pre task Id为对应Id
     *
     * @param proList
     * @param list
     * @param idMapping
     */
    private static void setChildPreTaskId(List<GanttTasksDto> proList, List<GanttTasksDto> list, Map<String, String> idMapping) {
        for (GanttTasksDto model : proList) {
            String preTaskId = "";
            if (!ValidateUtil.isEmpty(model.getTaskPreTaskId())) {
                String[] sk = model.getTaskPreTaskId().split(",");
                for (int i = 0; i < sk.length; i++) {
                    preTaskId += idMapping.get(sk[i]);
                    if (i != sk.length - 1)
                        preTaskId += ",";
                }
            }
            model.setTaskPreTaskId(preTaskId);
            List<GanttTasksDto> child = new ArrayList<>();
            if (!CollectionUtils.isEmpty(model.getChildNodes())) {
                setChildPreTaskId(model.getChildNodes(), child, idMapping);
            }
            model.setChildNodes(child);
            list.add(model);
        }
    }

    private static void getChildTasks(List<Task> tasks, int level, List<GanttTasksDto> proList, String parentId
            , Map<String, String> idMapping, List<SysDictionary> dictionaries, List<GanttLinksDTO> links, int sort) {
        if (tasks != null && tasks.size() > 0) {
            int levelNum = level += 1;
            for (Task task : tasks) {
                GanttTasksDto model = new GanttTasksDto();
                model.setTaskParent(parentId);
                model.setTaskText(task.getName());
                String taskDuration = task.getDuration().toString().trim().replace("d", "").replace("?", "");
                model.setTaskDuration(Float.valueOf(taskDuration));
                model.setTaskOutlineLevel(levelNum + "");
                model.setTaskStartDate(task.getStart());
                model.setTaskEndDate(task.getFinish());
                model.setTaskPriority("正常");
                model.setTaskId(GenerateUUID.getUUID());
                model.setTaskRemark(task.getNotes());
                model.setTaskNumber(task.getID().toString());
                model.setTaskSort(sort += 1);
                if (task.getPredecessors() != null) {
                    List<Relation> data = task.getPredecessors();
                    if (data != null && data.size() > 0) {
                        String preNumber = "";
                        for (int i = 0; i < data.size(); i++) {
                            GanttLinksDTO link = new GanttLinksDTO();
                            link.setLinkId(GenerateUUID.getUUID());
                            link.setLinkSource(data.get(i).getTargetTask().getID() + "");
                            link.setLinkTarget(data.get(i).getSourceTask().getID() + "");
                            int finalI = i;
                            List<SysDictionary> types = dictionaries.stream().filter(n -> n.getDName().equalsIgnoreCase(data.get(finalI).getType().toString())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(types)) {
                                link.setLinkType(Integer.parseInt(types.stream().findFirst().get().getDCode()));
                                links.add(link);
                            }
                            preNumber += data.get(i).getTargetTask().getID();
                            if (i != data.size() - 1)
                                preNumber += ",";
                        }
                        model.setTaskPreTaskId(preNumber);
                    }
                }
                idMapping.put(task.getID().toString(), model.getTaskId());
                List<GanttTasksDto> child = new ArrayList<>();
                if (task.getChildTasks().size() > 0) {
                    getChildTasks(task.getChildTasks(), levelNum, child, model.getTaskId(), idMapping, dictionaries, links, sort);
                }
                model.setChildNodes(child);
                proList.add(model);
            }
        }
    }
}
