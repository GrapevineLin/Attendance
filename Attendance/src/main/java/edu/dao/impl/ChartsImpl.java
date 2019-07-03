package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.EmployeeCharts;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChartsImpl {


    public List<EmployeeCharts> list() {
        List<EmployeeCharts> list = new ArrayList<EmployeeCharts>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("SELECT age,COUNT(*) as count FROM employee GROUP BY age");

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            while (rs.next()) {
                list.add(toBean(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }

    private EmployeeCharts toBean(ResultSet rs) {
        EmployeeCharts bean = new EmployeeCharts();

        try {
            bean.setAge(rs.getLong("age"));
            bean.setCount(rs.getLong("count"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}
