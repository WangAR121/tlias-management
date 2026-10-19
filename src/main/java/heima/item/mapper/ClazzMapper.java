package heima.item.mapper;

import heima.item.pojo.Clazz;
import heima.item.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {
    public List<Clazz> findAll(ClazzQueryParam classQueryParam);
    @Delete("delete from clazz where id=#{id}")
    void deleteById(Integer id);
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, create_time, update_time, subject) " +
            "values(#{name},#{room},#{beginDate},#{endDate},#{masterId},#{createTime},#{updateTime},#{subject})")
    void insert(Clazz clazz);
    @Select("select count(*) from student s where s.clazz_id=#{id} ")
    long countByClazzId(Integer id);
    @Select("select * from clazz where id=#{id}")
    Clazz selectById(Integer id);

    void update(Clazz clazz);
    @Select("select * from clazz")
    List<Clazz> findAllClazz();
}
