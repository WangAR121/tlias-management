package heima.item.mapper;

import heima.item.pojo.StuQueryParam;
import heima.item.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface StuMapper {
    List<Map<String, Object>> getStudentData();

    List<Student> findAll(StuQueryParam stuQueryParam);

    void delete(List<Integer> list);
    @Insert("insert into student(name, no, gender, phone, degree, id_card, is_college, address, graduation_date, clazz_id, create_time, update_time) " +
            "values (#{name},#{no},#{gender},#{phone},#{degree},#{idCard},#{isCollege},#{address},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void insert(Student student);
    @Select("select * from student where id=#{id}")
    Student selectById(Integer id);

    void update(Student student);
    @Update("update student set violation_score=violation_score+#{score},violation_count=violation_count+1,update_time=#{updateTime} where id=#{id}")
    void updateViolation(Integer id, Integer score, LocalDateTime updateTime);

    List<Map<String, Object>> getStuCount();
}
