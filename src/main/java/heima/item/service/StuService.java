package heima.item.service;

import heima.item.pojo.PageResult;
import heima.item.pojo.StuQueryParam;
import heima.item.pojo.Student;

import java.util.List;

public interface StuService {

    PageResult<Student> findAll(StuQueryParam stuQueryParam);

    void delete(List<Integer> list);

    void insert(Student student);

    Student selectById(Integer id);

    void update(Student student);

    void updateViolation(Integer id, Integer score);
}
