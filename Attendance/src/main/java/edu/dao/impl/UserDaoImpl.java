package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.User;
import edu.dao.UserDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private User toBean(ResultSet rs) {
        User bean = new User();
        try {
            bean.setId(rs.getLong("id"));
            bean.setUserName(rs.getString("userName"));
            bean.setPassWord(rs.getString("passWord"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bean;
    }

    @Override
    public Long insert(UserDao bean) {
        return null;
    }

    @Override
    public User LoadByName(String name) {
        User bean = null;
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        sbSQL.append("select * from tlogin ");
        sbSQL.append("where userName = ? ");
        sbSQL.append(" order by id asc");
        paramsList.add(name);
        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            if (rs.next()) {
                bean = toBean(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }
}