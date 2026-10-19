package heima.item.controller;

import heima.item.anno.Log;
import heima.item.pojo.PageResult;
import heima.item.pojo.Result;
import heima.item.pojo.StuQueryParam;
import heima.item.pojo.Student;
import heima.item.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StuController {
    @Autowired
    private StuService stuService;
    @GetMapping
    public Result findAll(StuQueryParam stuQueryParam){
        PageResult<Student> page=stuService.findAll(stuQueryParam);
        return Result.success(page);
    }
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        List<Integer> list= Arrays.stream(ids.split(",")).map(Integer::valueOf).toList();
        stuService.delete(list);
        return Result.success();
    }
    @Log
    @PostMapping
    public Result insert(@RequestBody Student student){
        stuService.insert(student);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        Student student=stuService.selectById(id);
        return Result.success(student);
    }
    @Log
    @PutMapping
    public Result update(@RequestBody Student student){
        stuService.update(student);
        return Result.success();
    }
    @Log
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id,@PathVariable Integer score){
        stuService.updateViolation(id,score);
        return Result.success();
    }
}
