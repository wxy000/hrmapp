package com.wxy.dao.provider;

import com.wxy.domain.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.wxy.util.common.HrmConstants.USERTABLE;

public class UserDynaSqlProvider {

    public String selectWithParam(Map<String, Object> params){
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(USERTABLE);
                if (params.get("user") != null){
                    User user = (User) params.get("user");
                    if (user.getUsername() != null && !user.getUsername().equals("")){
                        WHERE(" username LIKE CONCAT ('%', #{user.username}, '%') ");
                    }
                    if (user.getState() != null && !user.getState().equals("")){
                        WHERE(" state LIKE CONCAT ('%', #{user.state}, '%') ");
                    }
                }
            }
        }.toString();
        if (params.get("pageModel") != null){
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize} ";
        }
        return sql;
    }

    public String count(Map<String, Object> params){
        return new SQL(){
            {
                SELECT("count(*)");
                FROM(USERTABLE);
                if (params.get("user") != null){
                    User user = (User) params.get("user");
                    if (user.getUsername() != null && !user.getUsername().equals("")){
                        WHERE(" username LIKE CONCAT ('%', #{user.username}, '%') ");
                    }
                    if (user.getState() != null && !user.getState().equals("")){
                        WHERE(" state LIKE CONCAT ('%', #{user.state}, '%') ");
                    }
                }
            }
        }.toString();
    }

    public String insertUser(User user){
        return new SQL(){
            {
                INSERT_INTO(USERTABLE);
                if (user.getUsername() != null && !user.getUsername().equals("")){
                    VALUES("username", "#{username}");
                }
                if (user.getState() != null && !user.getState().equals("")){
                    VALUES("state", "#{state}");
                }
                if (user.getLoginname() != null && !user.getLoginname().equals("")){
                    VALUES("loginname", "#{loginname}");
                }
                if (user.getPassword() != null && !user.getPassword().equals("")){
                    VALUES("password", "#{password}");
                }
            }
        }.toString();
    }

    public String updateUser(User user){
        return new SQL(){
            {
                UPDATE(USERTABLE);
                if (user.getUsername() != null){
                    SET(" username = #{username} ");
                }
                if (user.getLoginname() != null){
                    SET(" loginname = #{loginname} ");
                }
                if (user.getPassword() != null){
                    SET(" password = #{password} ");
                }
                if (user.getState() != null){
                    SET(" state = #{state} ");
                }
                if (user.getCreateDate() != null){
                    SET(" create_date = #{createDate} ");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}
