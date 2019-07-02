package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Employee;
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
    public Long insert(Employee bean) {
        // TODO Auto-generated method stub
        Long result = 0L;

        // 0)定义变量
        StringBuffer sql = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1)组合SQL
        sql.append(" Insert Into tlogin");
        sql.append(" (");
        sql.append(" userName,passWord");
        sql.append(" )");
        sql.append(" values(?,?)");

        // 2)添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add("123456");


        // 3)转换类型
        String SQL = sql.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            // 4)取得连接对象
            conn = DbUtil.getConn();

            // 5)执行SQL
            Long num = DbFun.update(conn, SQL, params);

            // 6)结果处理
            if (num > 0) {
                SQL = "Select @@identity";
                result = DbFun.queryScalarLong(conn, SQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 9)关闭连接
            DbUtil.close(conn);
        }
        return result;
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