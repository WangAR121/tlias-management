package heima.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import heima.item.controller.EmpController;
import heima.item.mapper.EmpExprMapper;
import heima.item.mapper.EmpLogMapper;
import heima.item.mapper.EmpMapper;
import heima.item.pojo.*;
import heima.item.service.EmpService;
import heima.item.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogMapper empLogMapper;
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //原始分页
        /*Integer start=(page-1)*pageSize;
        long total =empMapper.count();
        List<Emp> rows=empMapper.list(start,pageSize);
        return new PageResult<Emp>(total,rows);*/
        //pagehelper
        //1.调用分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        List<Emp> empList=empMapper.list(empQueryParam);
        Page<Emp> p=(Page<Emp>) empList;
        return new PageResult(p.getTotal(),p.getResult());
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
        List<EmpExpr> exprList=emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr->{empExpr.setEmpId(emp.getId());});
            empExprMapper.insertBatch(exprList);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpId(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.selectById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        List<EmpExpr> exprlist=emp.getExprList();
        if(!CollectionUtils.isEmpty(exprlist)){
            empExprMapper.deleteByEmpId(Arrays.asList(emp.getId()));
            exprlist.forEach(empExpr -> {empExpr.setEmpId(emp.getId());});
            empExprMapper.insertBatch(exprlist);
        }
        empMapper.updateById(emp);
    }

    @Override
    public List<Emp> list() {
        return empMapper.findMasterName();
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp e=empMapper.selectByUsernameAndPassword(emp);
        if (e !=null){
            Map<String,Object>claims=new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String jwt=JwtUtils.generateToken(claims);
            log.info("登录成功,员工信息：{}",e);
            //登录日志：记录谁在什么时间登录成功
            empLogMapper.insert(new EmpLog(null, LocalDateTime.now(),
                    "员工 " + e.getName() + "（" + e.getUsername() + "）登录成功"));
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }
        //登录日志：登录失败也要留痕，方便排查异常登录
        String username = emp.getUsername() == null ? "（未填写账号）" : emp.getUsername();
        empLogMapper.insert(new EmpLog(null, LocalDateTime.now(),
                "账号 " + username + " 登录失败：用户名或密码错误"));
        return null;
    }
}
