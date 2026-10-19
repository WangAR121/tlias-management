package heima.item.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class Student {
    private Integer id;
    private String name;
    private String no;
    private Integer gender;
    private String phone;
    private Integer degree;
    private String idCard;
    private Integer isCollege;
    private String address;
    private LocalDate graduationDate;
    private Integer violationCount;
    private Integer violationScore;
    private Integer clazzId;
    private String clazzName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
