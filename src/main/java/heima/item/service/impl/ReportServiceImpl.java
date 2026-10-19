package heima.item.service.impl;

import heima.item.mapper.EmpMapper;
import heima.item.mapper.StuMapper;
import heima.item.pojo.JobOption;
import heima.item.pojo.clazzOption;
import heima.item.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StuMapper stuMapper;
    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> jobDataMap=empMapper.getEmpJobData();
        List<Object> jobList=jobDataMap.stream().map(mapData->{return mapData.get("pos");}).toList();
        List<Object> dataList=jobDataMap.stream().map(mapData->{return mapData.get("num");}).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countGenderData();
    }

    @Override
    public List<Map<String, Object>> getStudentData() {
        return  stuMapper.getStudentData();
    }

    @Override
    public clazzOption getStuCount() {
        List<Map<String,Object>> clazzOption=stuMapper.getStuCount();
        List<Object> clazzList=clazzOption.stream().map(clazz->{
            return clazz.get("clazzName");
        }).toList();
        List<Object> dataList=clazzOption.stream().map(clazz->{
            return clazz.get("value");
        }).toList();
        return new clazzOption(clazzList,dataList);
    }
}
