package heima.item.controller;

import heima.item.pojo.OperateLog;
import heima.item.pojo.OperateLogQueryParam;
import heima.item.pojo.PageResult;
import heima.item.pojo.Result;
import heima.item.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志查询：/log/page?page=1&pageSize=10
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/page")
    public Result page(OperateLogQueryParam queryParam) {
        log.info("分页查询操作日志，页码：{}，每页记录数：{}", queryParam.getPage(), queryParam.getPageSize());
        PageResult<OperateLog> pageResult = logService.page(queryParam);
        return Result.success(pageResult);
    }
}