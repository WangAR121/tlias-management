package heima.item.service;

import heima.item.pojo.JobOption;
import heima.item.pojo.clazzOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    List<Map<String, Object>> getStudentData();

    clazzOption getStuCount();
}
