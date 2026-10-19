package heima.item.service;

import heima.item.pojo.Emp;
import heima.item.pojo.EmpQueryParam;
import heima.item.pojo.LoginInfo;
import heima.item.pojo.PageResult;

import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    List<Emp> list();

    LoginInfo login(Emp emp);
}
