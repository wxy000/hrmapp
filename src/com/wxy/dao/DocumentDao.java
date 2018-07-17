package com.wxy.dao;

import com.wxy.dao.provider.DocumentDynaSqlProvider;
import com.wxy.domain.Document;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wxy.util.common.HrmConstants.DOCUMENTTABLE;

public interface DocumentDao {

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "insertDocument")
    void save(Document document);

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "updateDocument")
    void update(Document document);

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "selectWithParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_date", property = "createDate", javaType = Date.class),
            @Result(column = "user_id", property = "user", one = @One(select = "com.wxy.dao.UserDao.selectById", fetchType = FetchType.EAGER))
    })
    List<Document> selectByPage(Map<String, Object> params);

    @Select("select * from " + DOCUMENTTABLE + " where id = #{id}")
    Document selectById(Integer id);

    @Delete("delete from " + DOCUMENTTABLE + " where id = #{id}")
    void deleteById(Integer id);
}
