package heima.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private Integer degree;
    private Integer clazzId;
}
