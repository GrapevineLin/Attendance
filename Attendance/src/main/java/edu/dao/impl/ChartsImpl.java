package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.EmployeeCharts;
import edu.bean.RepairCharts;
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

    public List<RepairCharts> GetRepairCharts() {
        List<RepairCharts> list = new ArrayList<RepairCharts>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select E.empName as name,COUNT(*) as count ");
        sbSQL.append("from RepairCard R left join Employee E on ");
        sbSQL.append("E.empCode=R.empCode GROUP by name asc");

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            while (rs.next()) {
                list.add(toBeanR(rs));

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

    private RepairCharts toBeanR(ResultSet rs) {
        RepairCharts bean = new RepairCharts();
        try {
            bean.setName(rs.getString("name"));
            bean.setCount(rs.getLong("count"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}
