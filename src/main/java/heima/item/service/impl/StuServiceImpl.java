package heima.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import heima.item.mapper.StuMapper;
import heima.item.pojo.PageResult;
import heima.item.pojo.StuQueryParam;
import heima.item.pojo.Student;
import heima.item.service.StuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    @Override
    public PageResult<Student> findAll(StuQueryParam stuQueryParam) {
        PageHelper.startPage(stuQueryParam.getPage(),stuQueryParam.getPageSize());
        List<Student> studentList=stuMapper.findAll(stuQueryParam);
        Page<Student> page=(Page<Student>) studentList;
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(List<Integer> list) {
        stuMapper.delete(list);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.insert(student);
    }

    @Override
    public Student selectById(Integer id) {
         return stuMapper.selectById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.update(student);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateViolation(Integer id, Integer score) {
        LocalDateTime updateTime=LocalDateTime.now();
        stuMapper.updateViolation(id,score,updateTime);
    }
}
