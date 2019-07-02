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
    private Leave toBean(ResultSet rs) {
        Leave bean = new Leave();
        try {
            bean.setLeaveId(rs.getLong("leaveId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setBeginDate(rs.getTimestamp("beginDate"));
            bean.setEndDate(rs.getTimestamp("endDate"));
            bean.setReason(rs.getString("reason"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bean;
    }

    @Override
    public List<Leave> list() {
        List<Leave> list = new ArrayList<Leave>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select * from `leave`");

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

    @Override
    public Long insert(Leave bean) {
        Long result = 0L;

        // 0)定义变量
        StringBuffer sql = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1)组合SQL
        sql.append(" Insert Into `leave`");
        sql.append(" (");
        sql.append(" empCode,beginDate,endDate,reason");
        sql.append(" )");
        sql.append(" values(?,?,?,? )");

        // 2)添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add(bean.getBeginDate());
        paramsList.add(bean.getEndDate());
        paramsList.add(bean.getReason());



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
    public Long delete(Long id) {
        Long result = 0L;

        // 0）定义变量
        StringBuffer sql = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1) 组合SQL
        sql.append(" delete from `leave`");
        sql.append(" where leaveId=?");

        // 2) 添加参数
        paramsList.add(id);

        // 3) 转换类型
        String SQL = sql.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            // 4) 取得连接对象
            conn = DbUtil.getConn();

            // 5) 执行sql
            result = DbFun.update(conn, SQL, params);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 9) 关闭连接
            DbUtil.close(conn);
        }

        return result;
    }

    @Override
    public Long update(Leave bean) {
        Long result = 0L;

        // 0) 定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1) 组合SQL
        sbSQL.append(" update `leave` ");
        sbSQL.append(" set empCode=?, beginDate=?, endDate=?, reason=?");
        sbSQL.append(" where leaveId=?");

        // 2) 添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add(bean.getBeginDate());
        paramsList.add(bean.getEndDate());
        paramsList.add(bean.getReason());

        paramsList.add(bean.getLeaveId());


        // 3)转换类型
        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            // 4)取得连接对象
            conn = DbUtil.getConn();

            // 5)执行SQL
            result = DbFun.update(conn, sql, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 9) 关闭连接
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public Leave load(Long id) {
        Leave bean = null;
        //定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL

        sbSQL.append(" select * from `leave`");
        sbSQL.append(" where leaveId=?");


        //添加参数
        paramsList.add(id);

        //类型转换
        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs =null;


        try {
            //取得连接对象
            conn = DbUtil.getConn();

            //执行sql
            rs=DbFun.query(conn,sql,params);

            //单行转换为对象

            if(rs.next()){
                bean =toBean(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            //关闭
            DbUtil.close(conn);
        }
        return bean;
    }

    @Override
    public Leave loadByName(String name) {
        Leave bean = null;
        //定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sbSQL.append("select * from `leave` ");
        sbSQL.append("where empCode= ? ");
        sbSQL.append(" order by leaveId asc");

        //添加参数
        paramsList.add(name);

        //类型转换
        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs =null;


        try {
            //取得连接对象
            conn = DbUtil.getConn();

            //执行sql
            rs=DbFun.query(conn,sql,params);

            //单行转换为对象

            if(rs.next()){
                bean =toBean(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            //关闭
            DbUtil.close(conn);
        }
        return bean;
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
        Long result =0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();

        sbSQL.append("select count(1) From `leave` L");
        sbSQL.append(" left join Employee E on L.empCode=e.empCode");
        sbSQL.append(" where E.empCode like ? or E.empName like ? ");

        name = "%" + name + "%";
        paramsList.add(name);
        paramsList.add(name);

        String sql =sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            //取得连接对象
            conn = DbUtil.getConn();

            //执行sql
            result=DbFun.queryScalarLong(conn,sql,params);


        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            //关闭
            DbUtil.close(conn);
        }

        return result;
    }

    @Override
    public List<Leave> pagerByName(String name, Long pageNum, Long pageSize) {
        List<Leave> list = new  ArrayList<Leave>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();


        sbSQL.append(" select L.*,E.empName from `leave` L");
        sbSQL.append(" left join Employee E on E.empCode=L.empCode");
        sbSQL.append(" where E.empCode like ? or E.empName like ?");
        sbSQL.append(" order by leaveId asc");


        if(pageNum<1){
            pageNum=1L;
        }
        if(pageSize<1){
            pageSize=10L;
        }
        Long startIndex = (pageNum - 1)*pageSize;
        sbSQL.append(" limit "+startIndex+","+pageSize);

        name="%"+name+"%";
        paramsList.add(name);
        paramsList.add(name);

        String sql =sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            while (rs.next()) {
                list.add(toBeanEX(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }
}
