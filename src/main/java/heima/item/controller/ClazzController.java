package heima.item.controller;

import heima.item.anno.Log;
import heima.item.pojo.Clazz;
import heima.item.pojo.ClazzQueryParam;
import heima.item.pojo.PageResult;
import heima.item.pojo.Result;
import heima.item.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    @GetMapping
    public Result findAll(ClazzQueryParam clazzQueryParam){
        PageResult<Clazz> pageResult=clazzService.findAll(clazzQueryParam);
        return Result.success(pageResult);
    }
    @Log
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        clazzService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
      Clazz clazz=clazzService.selectById(id);
      return Result.success(clazz);
    }
    @GetMapping("/list")
    public Result findAllClazz(){
        List<Clazz> clazzList=clazzService.findAllClazz();
        return Result.success(clazzList);
    }
    @Log
    @PostMapping
    public Result insert(@RequestBody Clazz clazz){
        clazzService.insert(clazz);
        return Result.success();
    }
    @Log
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        clazzService.update(clazz);
        return Result.success();
    }
}
