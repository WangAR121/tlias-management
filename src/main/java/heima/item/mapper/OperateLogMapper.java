package heima.item.mapper;

import heima.item.pojo.OperateLog;
import heima.item.pojo.OperateLogQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime})")
    void insert(OperateLog log);

    //分页查询日志列表（关联 emp 表查询操作人姓名）
    List<OperateLog> page(OperateLogQueryParam queryParam);
}
