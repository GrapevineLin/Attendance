package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Leave;
import edu.dao.LeaveDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveDaoImpl implements LeaveDao {
    private Leave toBeanEX(ResultSet rs) {
        Leave bean = new Leave();
        try {
            bean.setLeaveId(rs.getLong("leaveId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setBeginDate(rs.getTimestamp("beginDate"));
            bean.setEndDate(rs.getTimestamp("endDate"));
            bean.setReason(rs.getString("reason"));
            bean.setEmpName(rs.getString("empName"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bean;
    }

    @Override
    public List<Leave> list() {
        return null;
    }

    @Override
    public Long insert(Leave bean) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public Long update(Leave bean) {
        return null;
    }

    @Override
    public Leave load(Long id) {
        return null;
    }

    @Override
    public Leave loadByName(String name) {
        return null;
    }

    @Override
    public Long count() {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select count(1) from `leave`");

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;

        try {
            conn = DbUtil.getConn();
            result = DbFun.queryScalarLong(conn, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public List<Leave> pager(Long pageNum, Long pageSize) {
        List<Leave> list = new ArrayList<Leave>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select L.*,E.empName from `leave` L");
        sbSQL.append(" left join Employee E on E.empCode=L.empCode");
        sbSQL.append(" order by leaveId asc");

        if (pageNum < 1) {
            pageNum = 1L;
        }
        if (pageSize < 1) {
            pageSize = 10L;
        }
        Long startIndex = (pageNum - 1) * pageSize;
        sbSQL.append(" limit " + startIndex + "," + pageSize);

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            while (rs.next()) {
                list.add(toBeanEX(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }

    @Override
    public Long countByName(String name) {
        return null;
    }

    @Override
    public List<Leave> pagerByName(String name, Long pageNum, Long pageSize) {
        return null;
    }
}
