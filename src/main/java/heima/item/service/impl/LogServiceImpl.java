package heima.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import heima.item.mapper.OperateLogMapper;
import heima.item.pojo.OperateLog;
import heima.item.pojo.OperateLogQueryParam;
import heima.item.pojo.PageResult;
import heima.item.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 异步保存日志：使用独立线程执行，即使业务方法抛出异常、事务回滚，
     * 日志依然可以落库，并且不会拖慢业务接口的响应速度。
     */
    @Async
    @Override
    public void save(OperateLog operateLog) {
        operateLogMapper.insert(operateLog);
        log.info("AOP记录操作日志：{}", operateLog);
    }

    @Override
    public PageResult<OperateLog> page(OperateLogQueryParam queryParam) {
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize());
        List<OperateLog> logList = operateLogMapper.page(queryParam);
        Page<OperateLog> page = (Page<OperateLog>) logList;
        return new PageResult<>(page.getTotal(), page.getResult());
    }
}
