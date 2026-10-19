package heima.item.controller;

import heima.item.anno.Log;
import heima.item.pojo.Emp;
import heima.item.pojo.EmpQueryParam;
import heima.item.pojo.PageResult;
import heima.item.pojo.Result;
import heima.item.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询{}，{},{},{},{}",empQueryParam.getPage(),empQueryParam.getPageSize(),empQueryParam.getBegin(),empQueryParam.getEnd(),empQueryParam.getName());
        PageResult<Emp> pageResult=empService.page(empQueryParam);
        return Result.success(pageResult);
    }
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("添加员工{}",emp);
        empService.save(emp);
        return Result.success();
    }
    @Log
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("根据id批量删除员工{}",ids);
        empService.delete(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询回显，根据id查询员工信息:{}",id);
        Emp emp=empService.getInfo(id);
        return Result.success(emp);
    }
    //clazz查询班主任
    @GetMapping("/list")
    public Result list(){
        List<Emp> empList=empService.list();
        return Result.success(empList);
    }
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工{}",emp);
        empService.update(emp);
        return Result.success();
    }
}
