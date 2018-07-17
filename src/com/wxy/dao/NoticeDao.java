package com.wxy.dao;

import com.wxy.dao.provider.NoticeDynaSqlProvider;
import com.wxy.domain.Notice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wxy.util.common.HrmConstants.NOTICETABLE;

public interface NoticeDao {

    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "insertNotice")
    void save(Notice notice);

    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "updateNotice")
    void update(Notice notice);

    @SelectProvider(type = NoticeDynaSqlProvider.class, method = "selectWithParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_date", property = "createDate", javaType = Date.class),
            @Result(column = "user_id", property = "user", one = @One(select = "com.wxy.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Notice> selectByPage(Map<String, Object> params);

    @Select("select * from " + NOTICETABLE + " where id = #{id}")
    Notice selectById(Integer id);

    @Delete("delete from "+ NOTICETABLE + " where id = #{id}")
    void deleteById(Integer id);
}
