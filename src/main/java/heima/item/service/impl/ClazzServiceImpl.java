package heima.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import heima.item.exception.DeleteClassException;
import heima.item.mapper.ClazzMapper;
import heima.item.pojo.Clazz;
import heima.item.pojo.ClazzQueryParam;
import heima.item.pojo.PageResult;
import heima.item.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public PageResult<Clazz> findAll(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());
        List<Clazz> classList= clazzMapper.findAll(clazzQueryParam);
        classList.stream().forEach(clazz -> {
            if(clazz.getEndDate().isBefore(LocalDate.now())){
                clazz.setStatus("已结课");
            }
            else if (clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开班");
            }
            else
                clazz.setStatus("在读中");
        });
        Page<Clazz> page=(Page<Clazz>) classList;
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        long count=clazzMapper.countByClazzId(id);
        if(count>0)
            throw new DeleteClassException("该班级有学生，无法直接删除");
        clazzMapper.deleteById(id);
    }

    @Override
    public void insert(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz selectById(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public List<Clazz> findAllClazz() {
        return clazzMapper.findAllClazz();
    }
}
