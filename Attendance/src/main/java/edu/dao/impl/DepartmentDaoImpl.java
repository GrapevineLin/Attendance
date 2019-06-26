package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Department;
import edu.dao.DepartmentDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    public List<Department> list() {

        List<Department> list = new ArrayList<Department>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select * from Department");

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

    public Long insert(Department bean) {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //SQL语句
        sbSQL.append(" Insert Into Department");
        sbSQL.append(" (");
        sbSQL.append(" depCode,depName,depHead,depResp");
        sbSQL.append(" ,supDepId");
        sbSQL.append(" )");
        sbSQL.append(" values(?,?,?,? ,?)");

        //添加参数
        paramsList.add(bean.getDepCode());
        paramsList.add(bean.getDepName());
        paramsList.add(bean.getDepHead());
        paramsList.add(bean.getDepResp());

        paramsList.add(bean.getSupDepId());

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
        sbSQL.append(" delete from Department");
        sbSQL.append(" where depId=?");

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

    public Long update(Department bean) {

        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // SQL语句
        sbSQL.append(" update Department ");
        sbSQL.append(" set depCode=?, depName=?, depHead=?, depResp=?,");
        sbSQL.append(" supDepId=?");
        sbSQL.append(" where depId=?");

        //添加参数
        paramsList.add(bean.getDepCode());
        paramsList.add(bean.getDepName());
        paramsList.add(bean.getDepHead());
        paramsList.add(bean.getDepResp());

        paramsList.add(bean.getSupDepId());

        paramsList.add(bean.getDepId());

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

    public Department load(Long id) {
        Department bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select D1.*,D2.depName as supDepName from Department D1");
        sbSQL.append(" left join Department D2 on D1.supDepId=D2.depId");
        sbSQL.append(" where D1.depId=?");

        paramsList.add(id);

        String sql = sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            if (rs.next()) {
                bean = toBeanDep(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    public Department loadByName(String name) {
        Department bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select D1.*,D2.depName as supDepName from Department D1");
        sbSQL.append(" left join Department D2 on D1.supDepId=D2.depId");
        sbSQL.append(" where D1.depName like ? ");
        sbSQL.append(" or D1.depid like ?");

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
                bean = toBean(rs);
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

        sbSQL.append("select count(1) from Department");

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

    public List<Department> pager(Long pageNum, Long pageSize) {
        List<Department> list = new ArrayList<Department>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select D1.*,D2.depName as supDepName from Department D1");
        sbSQL.append(" left join Department D2 on D1.supDepId=D2.depId");
        sbSQL.append(" order by D1.depId asc");

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
                list.add(toBeanDep(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }

    public Long countByName(String name) {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select count(1) from Department");
        sbSQL.append(" where depName like ? or depCode like ? ");

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

    public List<Department> pagerByName(String name, Long pageNum, Long pageSize) {
        List<Department> bean = new ArrayList<Department>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select D1.*,D2.depName as supDepName from Department D1");
        sbSQL.append(" left join Department D2 on D1.supDepId=D2.depId");
        sbSQL.append(" where D1.depName like ?");
        sbSQL.append(" or D1.depCode like ?");
        sbSQL.append(" order by D1.depId asc");

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
                bean.add(toBeanDep(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    private Department toBean(ResultSet rs) {
        Department bean = new Department();

        try {
            bean.setDepId(rs.getLong("depId"));
            bean.setDepCode(rs.getString("depCode"));
            bean.setDepName(rs.getString("depName"));
            bean.setDepHead(rs.getString("depHead"));
            bean.setDepResp(rs.getString("depResp"));
            bean.setSupDepId(rs.getLong("supDepId"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }

    private Department toBeanDep(ResultSet rs) {
        Department bean = new Department();

        try {
            bean.setDepId(rs.getLong("depId"));
            bean.setDepCode(rs.getString("depCode"));
            bean.setDepName(rs.getString("depName"));
            bean.setDepHead(rs.getString("depHead"));
            bean.setDepResp(rs.getString("depResp"));
            bean.setSupDepId(rs.getLong("supDepId"));
            bean.setSupDepName(rs.getString("supDepName"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}