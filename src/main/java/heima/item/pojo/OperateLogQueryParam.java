package heima.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志分页查询参数：/log/page?page=1&pageSize=10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLogQueryParam {
    private Integer page = 1; //页码
    private Integer pageSize = 10; //每页记录数
}
