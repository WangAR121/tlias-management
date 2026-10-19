package heima.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志实体类，对应 operate_log 表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLog {
    private Integer id; //ID
    private Integer operateEmpId; //操作人ID
    private LocalDateTime operateTime; //操作时间
    private String className; //操作的类名
    private String methodName; //操作的方法名
    private String methodParams; //方法参数
    private String returnValue; //返回值
    private Long costTime; //耗时，单位毫秒

    private String operateEmpName; //操作人姓名，来自 emp 表关联查询，数据库表没有该字段
}
