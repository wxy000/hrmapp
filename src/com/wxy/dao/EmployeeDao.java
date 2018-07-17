package com.wxy.dao;

import com.wxy.dao.provider.EmployeeDynaSqlProvider;
import com.wxy.domain.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wxy.util.common.HrmConstants.EMPLOYEETABLE;

public interface EmployeeDao {

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "insertEmployee")
    void save(Employee employee);

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "updateEmployee")
    void update(Employee employee);

    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "selectWithParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "post_code", property = "postCode"),
            @Result(column = "qq_num", property = "qqNum"),
            @Result(column = "birthday", property = "birthday", javaType = Date.class),
            @Result(column = "create_date", property = "createDate", javaType = Date.class),
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.wxy.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
            @Result(column = "job_id", property = "job", one = @One(select = "com.wxy.dao.JobDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Employee> selectByPage(Map<String, Object> params);

    @Delete("delete from " + EMPLOYEETABLE + " where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from " + EMPLOYEETABLE + " where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "post_code", property = "postCode"),
            @Result(column = "qq_num", property = "qqNum"),
            @Result(column = "birthday", property = "birthday", javaType = Date.class),
            @Result(column = "create_date", property = "createDate", javaType = Date.class),
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.wxy.dao.DeptDao.selectById", fetchType = FetchType.EAGER)),
            @Result(column = "job_id", property = "job", one = @One(select = "com.wxy.dao.JobDao.selectById", fetchType = FetchType.EAGER))
    })
    Employee selectById(Integer id);
}
