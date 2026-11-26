package com.jpmsoft.jpbpm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.HolidayVacationsMapper;
import com.jpmsoft.jpbpm.pojo.dto.HolidayVacationsDto;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.entity.HolidayVacations;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import com.jpmsoft.jpbpm.service.IHolidayVacationsService;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.jpmsoft.jpbpm.util.DateUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HolidayVacationsImpl extends ServiceImpl<HolidayVacationsMapper, HolidayVacations> implements IHolidayVacationsService {

    @Autowired
    SysConfigEntityService sysConfigEntityService;

    @Override
    public List<TaskResponse> timeEfficiencyCalculation(List<TaskResponse> oldList, Date minTime, Date currentTime, List<FlowActivity> activityList, String customerId) {
        List<TaskResponse> list = new ArrayList<>();
        QueryWrapper<HolidayVacations> wrapperr = new QueryWrapper<>();

        wrapperr.lt("Hv_Day", DateUtil.DateToString(DateUtil.DateChangeDay(currentTime, 1), "yyyy-MM-dd"));
        wrapperr.gt("Hv_Day", DateUtil.DateToString(DateUtil.DateChangeDay(minTime, -1), "yyyy-MM-dd"));
        List<HolidayVacations> holidayList = baseMapper.selectList(wrapperr);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalTimeSetting");//审批时效设置
        String festivalHoliday = "1";//"是否排除节假日",1：不排除，2：排除
        String workTimeType = "ALL";
        String workShift = "";
        String closingTime = "";
        String noonBreak = "1";
        String noonBreakStartTime = "12:00:00";
        String noonBreakEndTime = "12:00:00";
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalTimeSetting") != null) {
            String data = smsTemplateList.get("approvalTimeSetting").toString();
            Object obj = JacksonUtils.parseObject(data, new TypeReference<Map<String, String>>() {
            });
            if (((Map) obj).get("festivalHoliday") != null) {
                festivalHoliday = ((Map) obj).get("festivalHoliday").toString();
            }
            if (((Map) obj).get("workShift") != null) {
                workShift = ((Map) obj).get("workShift").toString();
            }
            if (((Map) obj).get("closingTime") != null) {
                closingTime = ((Map) obj).get("closingTime").toString();
            }
            if (((Map) obj).get("noonBreakStartTime") != null) {
                noonBreakStartTime = ((Map) obj).get("noonBreakStartTime").toString();
            }
            if (((Map) obj).get("noonBreakEndTime") != null) {
                noonBreakEndTime = ((Map) obj).get("noonBreakEndTime").toString();
            }
        }
        if (ValidateUtil.isEmpty(workShift)) {
            workShift = "0:00:00";
        }
        if (ValidateUtil.isEmpty(closingTime)) {
            closingTime = "23:59:59";
        }
        if (workShift.equals("0:00") && closingTime.equals("59:59")) {
            workTimeType = "ALL";
        } else {
            workTimeType = "1";
        }
        if (ValidateUtil.isEmpty(noonBreakStartTime)) {
            noonBreakStartTime = "12:00:00";
        }
        if (ValidateUtil.isEmpty(noonBreakEndTime)) {
            noonBreakEndTime = "12:00:00";
        }
        if (noonBreakEndTime.equals(noonBreakStartTime)) {
            workTimeType = "ALL";
        } else {
            workTimeType = "1";
        }
        workShift = dateCovering(workShift);
        closingTime = dateCovering(closingTime);
        noonBreakStartTime = dateCovering(noonBreakStartTime);
        noonBreakEndTime = dateCovering(noonBreakEndTime);
        for (int i = 0; i < oldList.size(); i++) {
            TaskResponse obj = oldList.get(i);
            Date newMinTime = obj.getTasksendtime();//当前任务发送时间
            BigDecimal timeConsuming = BigDecimal.ZERO;
            try {
                timeConsuming = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.DateToString(currentTime, "yyyy-MM-dd HH:mm:ss"));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (holidayList.size() > 0) {
                String startTime = DateUtil.DateToString(newMinTime, "yyyy-MM-dd");
                String endTime = DateUtil.DateToString(currentTime, "yyyy-MM-dd");
                List<HolidayVacations> holidays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(startTime, "yyyy-MM-dd")) > 0 && n.getHvDay().compareTo(DateUtil.String2Date(endTime, "yyyy-MM-dd")) < 0).collect(Collectors.toList());
                List<HolidayVacations> startdays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(startTime, "yyyy-MM-dd")) == 0).collect(Collectors.toList());
                List<HolidayVacations> enddays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(endTime, "yyyy-MM-dd")) == 0).collect(Collectors.toList());
                //计算需要去掉的节假日等非上班时间
                if ("2".equals(festivalHoliday)) {
                    if (startTime.equals(endTime)) {
                        // timeConsuming = BigDecimal.ZERO;
                    } else {
                        //需要去掉节假日
                        if (holidays.size() > 0) {
                            List<HolidayVacations> vacations = holidays.stream().filter(o -> o.getHvWeekday() == 2).collect(Collectors.toList());
                            if (vacations.size() > 0) {
                                BigDecimal hourTotal = BigDecimal.valueOf(vacations.size() * 24);
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            }
                        }
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() == 2) {
                                try {
                                    BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"), startTime + " 23:59:59");
                                    timeConsuming = timeConsuming.subtract(hourTotal);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() == 2) {
                                try {
                                    BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " 0:00:00", DateUtil.DateToString(currentTime, "yyyy-MM-dd HH:mm:ss"));
                                    timeConsuming = timeConsuming.subtract(hourTotal);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }


                if ("1".equals(workTimeType)) {
                    //需要去掉工作日的非上班时间
                    if (holidays.size() > 0) {
                        List<HolidayVacations> weeks = holidayList.stream().filter(o -> o.getHvWeekday() == 1).collect(Collectors.toList());
                        if (weeks.size() > 0) {
                            //计算
                            try {
                                BigDecimal hourTotal = BigDecimal.valueOf(24).subtract(DateUtil.getDifferHour("2023-11-09 " + workShift, "2023-11-09 " + closingTime)).multiply(BigDecimal.valueOf(weeks.size()));
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (startTime.equals(endTime)) {
                        //两个时间在同一天
                        if (startdays.size() > 0 || enddays.size() > 0) {
                            try {
                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) < 0) {
                                    //开始时间小于上班时间，并且结束时间小于上班时间
                                    timeConsuming = BigDecimal.ZERO;
                                }

                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                    //开始时间小于上班时间,并且结束时间大于上班时间，并且结束时间小于下班时间
                                    BigDecimal beHourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"), startTime + " " + workShift);
                                    timeConsuming = timeConsuming.subtract(beHourTotal);
                                }
                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间小于上班时间，并且结束时间大于下班时间
                                    BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + closingTime, DateUtil.DateToString(currentTime, "yyyy-MM-dd hh:mm:ss"));
                                    BigDecimal beHourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"), startTime + " " + workShift);
                                    timeConsuming = timeConsuming.subtract(hourTotal).subtract(beHourTotal);
                                }
                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                    //开始时间大于上班时间，并且结束实现小于下班时间

                                }
                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && newMinTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间大于上班时间，并且开始时间小于下班班时间，并且结束时间大于下班时间
                                    BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + closingTime, DateUtil.DateToString(currentTime, "yyyy-MM-dd HH:mm:ss"));
                                    timeConsuming = timeConsuming.subtract(hourTotal);
                                }
                                if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间大于下班时间，结束时间大于下班时间
                                    timeConsuming = BigDecimal.ZERO;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    } else {
                        //两个时间不在同一天
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() == 1) {
                                try {
                                    //如果开始时间小于上班时间
                                    if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + workShift, DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"));
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //如果开始时间大于下班时间
                                    if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) >=0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd HH:mm:ss"), startTime + " " + closingTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() == 1) {
                                try {
                                    try {
                                        //如果开始时间小于上班时间
                                        if (currentTime.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                            BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " 0:00:00", DateUtil.DateToString(currentTime, "yyyy-MM-dd HH:mm:ss"));
                                            timeConsuming = timeConsuming.subtract(hourTotal);
                                        }
                                        //如果开始时间大于下班时间
                                        if (currentTime.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) >= 0) {
                                            BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + closingTime, DateUtil.DateToString(currentTime, "yyyy-MM-dd HH:mm:ss"));
                                            timeConsuming = timeConsuming.subtract(hourTotal);
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                }
                if ("1".equals(noonBreak)) {
                    if (holidays.size() > 0) {
                        //需要去掉工作日的非上班时间
                        List<HolidayVacations> weeks = holidayList.stream().filter(o -> o.getHvWeekday() == 1).collect(Collectors.toList());
                        if (weeks.size() > 0) {
                            //计算
                            try {
                                BigDecimal hourTotal = DateUtil.getDifferHour("2023-11-09 " + noonBreakStartTime, "2023-11-09 " + noonBreakEndTime).multiply(BigDecimal.valueOf(weeks.size()));
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    if (startTime.equals(endTime)) {
                        try {
                            if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                //开始时间小于午休开始时间，并且结束时间大于午休开始时间，并且结束时间小于午休结束时间
                                BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakEndTime, DateUtil.DateToString(currentTime, "yyyy-MM-dd hh:mm:ss"));
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }
                            if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0) {
                                //开始时间小于 午休开始时间，并且结束时间大于午休结束时间

                                BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakStartTime, startTime + " " + noonBreakEndTime);
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }

                            if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0) {
                                //开始时间大于午休开始时间，并且开始时间小于午休结束时间，并且结束时间大于午休结束时间
                                BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd hh:mm:ss"), startTime + " " + noonBreakEndTime);
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        //不是同一天
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() == 1) {
                                try {
                                    //开始时间小于午休开始时间
                                    if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakStartTime, startTime + " " + noonBreakEndTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //开始时间大于午休开始时间，并且开始时间小于午休结束时间
                                    if (newMinTime.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(newMinTime, "yyyy-MM-dd hh:mm:ss"), startTime + " " + noonBreakStartTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() == 1) {
                                try {
                                    //结束时间大于午休结束时间
                                    if (currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + noonBreakStartTime, endTime + " " + noonBreakEndTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //结束时间大于午休开始时间，并且小于午休结束时间
                                    if (currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && currentTime.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + noonBreakStartTime, DateUtil.DateToString(currentTime, "yyyy-MM-dd hh:mm:ss"));
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                }
            }
            //计算剩余时间
            List<FlowActivity> acts = activityList.stream().filter(k -> k.getActid().equals(obj.getTaskcurrentactid())).collect(Collectors.toList());
            if (acts.size() > 0) {
                if (acts.get(0).getActhour() != null) {
                    BigDecimal hour = BigDecimal.valueOf(acts.get(0).getActhour());
                    if (timeConsuming.compareTo(BigDecimal.ZERO) < 0) {
                        timeConsuming = BigDecimal.ZERO;
                    }
                    timeConsuming = hour.subtract(timeConsuming);
                } else {
                    timeConsuming = BigDecimal.ZERO;
                }
            } else {
                timeConsuming = BigDecimal.ZERO;
            }
//            if(timeConsuming.compareTo(BigDecimal.ZERO)<0){
//                timeConsuming=BigDecimal.ZERO;
//            }
            obj.setRemainder(timeConsuming);
            list.add(obj);

        }

        return list;
    }

    @Override
    public String computingTime(HolidayVacationsDto request, String customerId) {
        QueryWrapper<HolidayVacations> wrapperr = new QueryWrapper<>();
        Date start = request.getStartTime();
        Date end = request.getEndTime();
        String calculationType = request.getCalculationType();
        String type = request.getType();
        int number = request.getNumber();
        if (ValidateUtil.isNull(calculationType)) {
            return "请传入计算类型";
        }
        if (ValidateUtil.isEmpty(type)) {
            return "请传入计算单位";
        }
        if ("1".equals(calculationType)) {
            if (start == null || end == null) {
                return "请传入开始时间或结束时间";
            }
        }
        if ("2".equals(calculationType)) {
            if (number == 0) {
                return "请填写加减天数";
            }
            //计算需要查询的天数
            if ("day".equals(type)) {
                end = DateUtil.DateChangeDay(start, number * 2 + 30);
            }
            if ("hour".equals(type)) {
                end = DateUtil.DateChangeHour(start, number * 2 + 24 * 30);
            }
        }
        if ("3".equals(calculationType)) {
            if (number == 0) {
                return "请填写加减天数";
            }
            if ("day".equals(type)) {
                start = DateUtil.DateChangeDay(end, 0 - (number * 2 + 30));
            }
            if ("hour".equals(type)) {
                start = DateUtil.DateChangeHour(end, 0 - (number * 2 + 24 * 30));
            }
        }

        wrapperr.lt("Hv_Day", DateUtil.DateToString(DateUtil.DateChangeDay(end, 1), "yyyy-MM-dd"));
        wrapperr.gt("Hv_Day", DateUtil.DateToString(DateUtil.DateChangeDay(start, -1), "yyyy-MM-dd"));
        List<HolidayVacations> holidayList = baseMapper.selectList(wrapperr);
        List<String> codeList = new ArrayList<>();
        codeList.add("approvalTimeSetting");//审批时效设置
        String festivalHoliday = "1";//"是否排除节假日",1：不排除，2：排除
        String workTimeType = "ALL";
        String workShift = "";
        String closingTime = "";
        String noonBreak = "1";
        String noonBreakStartTime = "12:00:00";
        String noonBreakEndTime = "12:00:00";
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if (smsTemplateList.get("approvalTimeSetting") != null) {
            String data = smsTemplateList.get("approvalTimeSetting").toString();
            Object obj = JacksonUtils.parseObject(data, new TypeReference<Map<String, String>>() {
            });
            if (((Map) obj).get("festivalHoliday") != null) {
                festivalHoliday = ((Map) obj).get("festivalHoliday").toString();
            }
            if (((Map) obj).get("workShift") != null) {
                workShift = ((Map) obj).get("workShift").toString();
            }
            if (((Map) obj).get("closingTime") != null) {
                closingTime = ((Map) obj).get("closingTime").toString();
            }
            if (((Map) obj).get("noonBreakStartTime") != null) {
                noonBreakStartTime = ((Map) obj).get("noonBreakStartTime").toString();
            }
            if (((Map) obj).get("noonBreakEndTime") != null) {
                noonBreakEndTime = ((Map) obj).get("noonBreakEndTime").toString();
            }
        }
        if (ValidateUtil.isEmpty(workShift)) {
            workShift = "0:00:00";
        }
        if (ValidateUtil.isEmpty(closingTime)) {
            closingTime = "23:59:59";
        }
        if (workShift.equals("0:00") && closingTime.equals("59:59")) {
            workTimeType = "ALL";
        } else {
            workTimeType = "1";
        }
        if (ValidateUtil.isEmpty(noonBreakStartTime)) {
            noonBreakStartTime = "12:00:00";
        }
        if (ValidateUtil.isEmpty(noonBreakEndTime)) {
            noonBreakEndTime = "12:00:00";
        }
        if (noonBreakEndTime.equals(noonBreakStartTime)) {
            workTimeType = "ALL";
        } else {
            workTimeType = "1";
        }


        if (ValidateUtil.isNotEmpty(request.getFestivalHoliday())) {
            festivalHoliday = request.getFestivalHoliday();
        }
        if (ValidateUtil.isNotEmpty(request.getWorkTimeType())) {
            workTimeType = request.getWorkTimeType();
        }
        if (ValidateUtil.isNotEmpty(request.getWorkShift())) {
            workShift = request.getWorkShift();
        }
        if (ValidateUtil.isNotEmpty(request.getClosingTime())) {
            closingTime = request.getClosingTime();
        }
        if (ValidateUtil.isNotEmpty(request.getNoonBreak())) {
            noonBreak = request.getNoonBreak();
        }
        if (ValidateUtil.isNotEmpty(request.getNoonBreakStartTime())) {
            noonBreakStartTime = request.getNoonBreakStartTime();
        }
        if (ValidateUtil.isNotEmpty(request.getNoonBreakEndTime())) {
            noonBreakEndTime = request.getNoonBreakEndTime();
        }
        if ("1".equals(calculationType)) {
            BigDecimal timeConsuming = BigDecimal.ZERO;
            if (holidayList.size() > 0) {
                String startTime = DateUtil.DateToString(start, "yyyy-MM-dd");
                String endTime = DateUtil.DateToString(end, "yyyy-MM-dd");
                List<HolidayVacations> holidays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(startTime, "yyyy-MM-dd")) > 0 && n.getHvDay().compareTo(DateUtil.String2Date(endTime, "yyyy-MM-dd")) < 0).collect(Collectors.toList());
                List<HolidayVacations> startdays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(startTime, "yyyy-MM-dd")) == 0).collect(Collectors.toList());
                List<HolidayVacations> enddays = holidayList.stream().filter(n -> n.getHvDay().compareTo(DateUtil.String2Date(endTime, "yyyy-MM-dd")) == 0).collect(Collectors.toList());
                //计算需要去掉的节假日等非上班时间
                BigDecimal dayTime = BigDecimal.valueOf(24.00);
                timeConsuming = BigDecimal.valueOf(24.00 * holidays.size());
                if ("2".equals(festivalHoliday)) {
                    if (startTime.equals(endTime)) {
                        try {
                            timeConsuming = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd HH:mm:ss"), DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        //需要去掉节假日
                        if (holidays.size() > 0) {
                            List<HolidayVacations> vacations = holidays.stream().filter(o -> o.getHvWeekday() == 2).collect(Collectors.toList());
                            if (vacations.size() > 0) {
                                BigDecimal hourTotal = BigDecimal.valueOf(vacations.size() * 24);
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            }
                        }
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() != 2) {
                                try {
                                    BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd HH:mm:ss"), startTime + " 23:59:59");
                                    timeConsuming = timeConsuming.add(hourTotal);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() != 2) {
                                try {
                                    BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " 0:00:00", DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                                    timeConsuming = timeConsuming.add(hourTotal);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }


                if ("1".equals(workTimeType)) {
                    //需要去掉工作日的非上班时间
                    if (holidays.size() > 0) {
                        List<HolidayVacations> weeks = holidays.stream().filter(o -> o.getHvWeekday() == 1).collect(Collectors.toList());
                        if (weeks.size() > 0) {
                            //计算
                            try {
                                BigDecimal hourTotal = BigDecimal.valueOf(24).subtract(DateUtil.getDifferHour("2023-11-09 " + workShift, "2023-11-09 " + closingTime)).multiply(BigDecimal.valueOf(weeks.size()));
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (startTime.equals(endTime)) {
                        //两个时间在同一天
                        if (startdays.size() > 0 || enddays.size() > 0) {
                            try {
                                if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && end.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) < 0) {
                                    //开始时间小于上班时间，并且结束时间小于上班时间
                                    timeConsuming = BigDecimal.ZERO;
                                }

                                if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && end.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                    //开始时间小于上班时间,并且结束时间大于上班时间，并且结束时间小于下班时间
                                    BigDecimal beHourTotal = DateUtil.getDifferHour(startTime + " " + workShift, DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                                    timeConsuming = beHourTotal;
                                }
                                if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间小于上班时间，并且结束时间大于下班时间
                                    BigDecimal beHourTotal = DateUtil.getDifferHour(startTime + " " + workShift, startTime + " " + closingTime);
                                    timeConsuming = beHourTotal;
                                }
                                if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                    //开始时间大于上班时间，并且结束实现小于下班时间
                                    BigDecimal beHourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd HH:mm:ss"), DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                                    timeConsuming = beHourTotal;
                                }
                                if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) <= 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间大于上班时间，并且开始时间小于下班班时间，并且结束时间大于下班时间
                                    BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd HH:mm:ss"), endTime + " " + closingTime);
                                    timeConsuming = hourTotal;
                                }
                                if (start.compareTo(DateUtil.String2Date(startTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) > 0) {
                                    //开始时间大于下班时间，结束时间大于下班时间
                                    timeConsuming = BigDecimal.ZERO;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    } else {
                        //两个时间不在同一天
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() == 1) {
                                try {
                                    //如果开始时间小于上班时间
                                    if (start.compareTo(DateUtil.String2Date(startTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + closingTime, startTime + " 23:59:59");
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //如果开始时间大于下班时间
                                    if (start.compareTo(DateUtil.String2Date(startTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) >= 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd HH:mm:ss"), startTime + " 23:59:59");
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() == 1) {
                                try {
                                    try {
                                        //如果开始时间小于上班时间
                                        if (end.compareTo(DateUtil.String2Date(endTime + " " + workShift, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                                            BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " 0:00:00", DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                                            timeConsuming = timeConsuming.subtract(hourTotal);
                                        }
                                        //如果开始时间大于下班时间
                                        if (end.compareTo(DateUtil.String2Date(endTime + " " + closingTime, "yyyy-MM-dd HH:mm:ss")) >= 0) {
                                            BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " 0:00:00", DateUtil.DateToString(end, "yyyy-MM-dd HH:mm:ss"));
                                            BigDecimal hourTotal1 = DateUtil.getDifferHour(endTime + " " + workShift, endTime + " " + closingTime);
                                            timeConsuming = timeConsuming.subtract(hourTotal).add(hourTotal1);
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                    try {
                        BigDecimal workTime = DateUtil.getDifferHour("2023-11-09 " + workShift, "2023-11-09 " + closingTime);
                        dayTime = workTime;
                    } catch (Exception ex) {

                    }
                }
                //是否午休
                if ("1".equals(noonBreak)) {
                    if (holidays.size() > 0) {
                        //需要去掉工作日的非上班时间
                        List<HolidayVacations> weeks = holidays.stream().filter(o -> o.getHvWeekday() == 1).collect(Collectors.toList());
                        if (weeks.size() > 0) {
                            //计算
                            try {
                                BigDecimal hourTotal = DateUtil.getDifferHour("2023-11-09 " + noonBreakStartTime, "2023-11-09 " + noonBreakEndTime).multiply(BigDecimal.valueOf(weeks.size()));
                                timeConsuming = timeConsuming.subtract(hourTotal);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    if (startTime.equals(endTime)) {
                        try {
                            if (start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && start.compareTo(DateUtil.String2Date(endTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                //开始时间小于午休开始时间，并且结束时间大于午休开始时间，并且结束时间小于午休结束时间
                                BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakStartTime, DateUtil.DateToString(end, "yyyy-MM-dd hh:mm:ss"));
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }
                            if (start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && start.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) >= 0) {
                                //开始时间小于 午休开始时间，并且结束时间大于午休结束时间

                                BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakStartTime, startTime + " " + noonBreakEndTime);
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }

                            if (start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) < 0 && start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) >= 0) {
                                //开始时间大于午休开始时间，并且开始时间小于午休结束时间，并且结束时间大于午休结束时间
                                BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd hh:mm:ss"), startTime + " " + noonBreakEndTime);
                                timeConsuming = timeConsuming.subtract(hourTotal);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        //不是同一天
                        if (startdays.size() > 0) {
                            if (startdays.get(0).getHvWeekday() == 1) {
                                try {
                                    //开始时间小于午休开始时间
                                    if (start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(startTime + " " + noonBreakStartTime, startTime + " " + noonBreakEndTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //开始时间大于午休开始时间，并且开始时间小于午休结束时间
                                    if (start.compareTo(DateUtil.String2Date(startTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(DateUtil.DateToString(start, "yyyy-MM-dd hh:mm:ss"), startTime + " " + noonBreakEndTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        if (enddays.size() > 0) {
                            if (enddays.get(0).getHvWeekday() == 1) {
                                try {
                                    //结束时间大于午休结束时间
                                    if (end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) > 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + noonBreakStartTime, endTime + " " + noonBreakEndTime);
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                    //结束时间大于午休开始时间，并且小于午休结束时间
                                    if (end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakStartTime, "yyyy-MM-dd hh:mm:ss")) > 0 && end.compareTo(DateUtil.String2Date(endTime + " " + noonBreakEndTime, "yyyy-MM-dd hh:mm:ss")) < 0) {
                                        BigDecimal hourTotal = DateUtil.getDifferHour(endTime + " " + noonBreakStartTime, DateUtil.DateToString(end, "yyyy-MM-dd hh:mm:ss"));
                                        timeConsuming = timeConsuming.subtract(hourTotal);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                    try {

                        BigDecimal hourTotal = DateUtil.getDifferHour("2023-11-09 " + noonBreakStartTime, "2023-11-09 " + noonBreakEndTime);
                        dayTime = dayTime.subtract(hourTotal);
                    } catch (Exception ex) {

                    }

                }
                if ("day".equals(type)) {
                    timeConsuming = timeConsuming.divide(dayTime, 2, BigDecimal.ROUND_HALF_UP);
                }

            }
            //计算剩余时间

            return timeConsuming + "";
        } else if ("2".equals(calculationType)) {

            //往前计算
            //计算一天有多少个小时
            BigDecimal timeConsuming = BigDecimal.ZERO;
            if (holidayList.size() > 0) {
                holidayList = holidayList.stream().sorted(Comparator.comparing(HolidayVacations::getHvDay)).collect(Collectors.toList());
                if ("day".equals(type)) {
                    //添加天数
                    int i = 0;
                    Date day = null;
                    for (int num = 0; num < holidayList.size(); num++) {
                        HolidayVacations hv = holidayList.get(num);
                        if (hv.getHvWeekday() == 1) {
                            i++;
                            //工作日
                            if (i == number - 1) {
                                day = hv.getHvDay();
                            }
                        }
                    }
                    //提取开始时间的时分秒部分
                    String str = DateUtil.DateToString(start, "hh:mm:ss");
                    String st = DateUtil.DateToString(day, "yyyy-MM-dd");
                    return st + "T" + str;
                }

            }

        } else if ("3".equals(calculationType)) {
            //往后计算
            //计算一天有多少小时
            if (holidayList.size() > 0) {
                holidayList = holidayList.stream().sorted(Comparator.comparing(HolidayVacations::getHvDay)).collect(Collectors.toList());
                if ("day".equals(type)) {
                    //添加天数
                    int i = 0;
                    Date day = null;
                    for (int num = holidayList.size(); num > 0; num--) {
                        HolidayVacations hv = holidayList.get(num);
                        if (hv.getHvWeekday() == 1) {
                            i++;
                            //工作日
                            if (i == number - 1) {
                                day = hv.getHvDay();
                            }
                        }
                    }
                    //提取开始时间的时分秒部分
                    String str = DateUtil.DateToString(start, "hh:mm:ss");
                    String st = DateUtil.DateToString(day, "yyyy-MM-dd");
                    return st + "T" + str;
                }

            }

        }
        return "";
    }


    public String dateCovering(String e) {
        String[] dates = e.split(":");
        if (dates.length == 2) {
            return e + ":00";
        } else {
            return e;
        }
    }
}
