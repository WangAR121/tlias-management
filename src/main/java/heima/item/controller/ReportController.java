package heima.item.controller;

import heima.item.pojo.JobOption;
import heima.item.pojo.Result;
import heima.item.pojo.clazzOption;
import heima.item.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        JobOption jobOption=reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别人数");
        List<Map<String,Object>> genderList=reportService.getEmpGenderData();
        return Result.success(genderList);
    }
    @GetMapping("/studentDegreeData")
    public Result getStudentData(){
        log.info("请求各学历学生人数");
        List<Map<String,Object>> studentList=reportService.getStudentData();
        return Result.success(studentList);
    }
    @GetMapping("/studentCountData")
    public Result getStuCount(){
        log.info("统计班级人数");
        clazzOption clazzOption=reportService.getStuCount();
        return Result.success(clazzOption);
    }
}
