package heima.item.service;

import heima.item.pojo.Clazz;
import heima.item.pojo.ClazzQueryParam;
import heima.item.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> findAll(ClazzQueryParam clazzQueryParam);

    void deleteById(Integer id);

    void insert(Clazz clazz);

    Clazz selectById(Integer id);

    void update(Clazz clazz);

    List<Clazz> findAllClazz();
}
