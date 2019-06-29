package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.PaySalary;
import edu.dao.PaySalaryDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaySalaryDaoImpl implements PaySalaryDao {
    public List<PaySalary> list() {

        List<PaySalary> list = new ArrayList<PaySalary>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select * from PaySalary");

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

    public Long insert(PaySalary bean) {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //SQL语句
        sbSQL.append(" Insert Into PaySalary");
        sbSQL.append(" (");
        sbSQL.append(" empId,beginDate,endDate,salary");
        sbSQL.append(" )");
        sbSQL.append(" values(?,?,?,?)");

        //添加参数
        paramsList.add(bean.getEmpId());
        paramsList.add(bean.getBeginDate());
        paramsList.add(bean.getEndDate());
        paramsList.add(bean.getSalary());

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            conn = DbUtil.getConn();

            //执行SQL
            Long num = DbFun.update(conn, sql, params);
            if (num > 0) {
                sql = "Select @@identity";
                result = DbFun.queryScalarLong(conn, sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return result;
    }

    public Long delete(Long id) {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // SQL语句
        sbSQL.append(" delete from PaySalary");
        sbSQL.append(" where payId=?");

        //添加参数
        paramsList.add(id);

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            result = DbFun.update(conn, sql, params);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }

        return result;
    }

    public Long update(PaySalary bean) {

        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // SQL语句
        sbSQL.append(" update PaySalary");
        sbSQL.append(" set empId=?, beginDate=?, endDate=?, salary=?");
        sbSQL.append(" where payId=?");

        //添加参数
        //添加参数
        paramsList.add(bean.getEmpId());
        paramsList.add(bean.getBeginDate());
        paramsList.add(bean.getEndDate());
        paramsList.add(bean.getSalary());

        paramsList.add(bean.getPayId());

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            result = DbFun.update(conn, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return result;
    }

    public PaySalary load(Long id) {
        PaySalary bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        //连接Station和Employee表
        sbSQL.append(" Select P.*,E.empName,E.empCode from PaySalary P");
        sbSQL.append(" left join Employee E on P.empId=E.empId");
        sbSQL.append(" where P.payId= ? ");
        sbSQL.append(" order by P.payId asc");

        paramsList.add(id);

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            if (rs.next()) {
                bean = toBeanEmp(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    public PaySalary loadByName(String name) {
        PaySalary bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        //连接Station和Employee表
        sbSQL.append(" Select P.*,E.empName,E.empCode from PaySalary P");
        sbSQL.append(" left join Employee E on P.empId=E.empId");
        sbSQL.append(" where E.empName like ? ");
        sbSQL.append(" or E.empCode like ?");

        paramsList.add("%"+name+"%");
        paramsList.add("%"+name+"%");

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);

            if (rs.next()) {
                bean = toBeanEmp(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    public Long count() {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select count(1) from PaySalary");

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

    public List<PaySalary> pager(Long pageNum, Long pageSize) {
        List<PaySalary> list = new ArrayList<PaySalary>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        //连接Station和Employee表
        sbSQL.append(" Select P.*,E.empName,E.empCode from PaySalary P");
        sbSQL.append(" left join Employee E on P.empId=E.empId");
        sbSQL.append(" order by P.payId asc");

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
                list.add(toBeanEmp(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }

    public Long countByName(String name)  {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        //连接Station和Employee表
        sbSQL.append(" Select count(1) from PaySalary P");
        sbSQL.append(" left join Employee E on P.empId=E.empId");
        sbSQL.append(" where E.empName like ? or E.empCode like ? ");

        name = "%" + name + "%";
        paramsList.add(name);
        paramsList.add(name);

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

    public List<PaySalary> pagerByName(String name, Long pageNum, Long pageSize) {
        List<PaySalary> bean = new ArrayList<PaySalary>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        //连接Station和Employee表
        sbSQL.append(" Select P.*,E.empName,E.empCode from PaySalary P");
        sbSQL.append(" left join Employee E on P.empId=E.empId");
        sbSQL.append(" where E.empName like ?");
        sbSQL.append(" or E.empCode like ?");
        sbSQL.append(" order by P.payId asc");

        if (pageNum < 1) {
            pageNum = 1L;
        }
        if (pageSize < 1) {
            pageSize = 10L;
        }
        Long startIndex = (pageNum - 1) * pageSize;
        sbSQL.append(" limit " + startIndex + "," + pageSize);

        name = "%" + name + "%";
        paramsList.add(name);
        paramsList.add(name);

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            while (rs.next()) {
                bean.add(toBeanEmp(rs));
            }
        } catch (SQLException e)    {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    private PaySalary toBean(ResultSet rs) {
        PaySalary bean = new PaySalary();

        try {
            bean.setPayId(rs.getLong("payId"));
            bean.setEmpId(rs.getLong("empId"));
            bean.setBeginDate(rs.getTimestamp("beginDate"));
            bean.setEndDate(rs.getTimestamp("endDate"));
            bean.setSalary(rs.getLong("salary"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }

    private PaySalary toBeanEmp(ResultSet rs) {
        PaySalary bean = new PaySalary();

        try {
            bean.setPayId(rs.getLong("payId"));
            bean.setEmpId(rs.getLong("empId"));
            bean.setBeginDate(rs.getTimestamp("beginDate"));
            bean.setEndDate(rs.getTimestamp("endDate"));
            bean.setSalary(rs.getLong("salary"));

            bean.setEmpName(rs.getString("empName"));
            bean.setEmpCode(rs.getString("empCode"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}