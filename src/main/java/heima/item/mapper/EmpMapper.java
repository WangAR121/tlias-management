package heima.item.mapper;

import heima.item.pojo.Emp;
import heima.item.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    //原始分页方法
    /*@Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    long count();
    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
            "order by e.update_time limit #{start},#{pageSize}")
    List<Emp> list(Integer start,Integer pageSize)*/;
    //pagehelper方法,在xml文件动态sql语句
    List<Emp> list(EmpQueryParam empQueryParam);
    //给emp的id属性赋值数据库里的id，以便service里给empexpr这个员工的id存在工作经历表的empId，来对应员工与他经历。
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{password},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp selectById(Integer id);

    void updateById(Emp emp);

    List<Map<String, Object>> getEmpJobData();

    List<Map<String, Object>> countGenderData();
    @Select("select * from emp where job=1")
    List<Emp> findMasterName();
    @Select("select id,username,name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
