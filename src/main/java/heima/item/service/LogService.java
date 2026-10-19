package heima.item.service;

import heima.item.pojo.OperateLog;
import heima.item.pojo.OperateLogQueryParam;
import heima.item.pojo.PageResult;

public interface LogService {

    //新增日志（异步）
    void save(OperateLog operateLog);

    //分页查询日志列表
    PageResult<OperateLog> page(OperateLogQueryParam queryParam);
}
