package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Station;
import edu.dao.StationDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDaoImpl implements StationDao {
    @Override
    public List<Station> list() {
        List<Station> list = new ArrayList<Station>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select * from Station");

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

    public Long insert(Station bean) {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //SQL语句
        sbSQL.append(" Insert Into Station");
        sbSQL.append(" (");
        sbSQL.append(" jobCode,jobName,dep,dirSup");
        sbSQL.append(" ,jobCat,jobDes");
        sbSQL.append(" )");
        sbSQL.append(" values(?,?,?,?,?,?)");

        //添加参数
        paramsList.add(bean.getJobCode());
        paramsList.add(bean.getJobName());
        paramsList.add(bean.getDep());
        paramsList.add(bean.getDirSup());
        paramsList.add(bean.getJobCat());
        paramsList.add(bean.getJobDes());

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
        sbSQL.append(" delete from Station");
        sbSQL.append(" where jobId=?");

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

    public Long update(Station bean) {

        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // SQL语句
        sbSQL.append(" update Station ");
        sbSQL.append(" set jobCode=?, jobName=?, dep=?, dirSup=?,");
        sbSQL.append(" jobCat=?,jobDes=?");
        sbSQL.append(" where jobId=?");

        //添加参数
        paramsList.add(bean.getJobCode());
        paramsList.add(bean.getJobName());
        paramsList.add(bean.getDep());
        paramsList.add(bean.getDirSup());
        paramsList.add(bean.getJobCat());
        paramsList.add(bean.getJobDes());
        paramsList.add(bean.getJobId());

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

    public Station load(Long id) {
        Station bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select * from Station");
        sbSQL.append(" where jobId=?");

        paramsList.add(id);

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

    public Station loadByName(String name) {
        Station bean = null;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select * from Station");
        sbSQL.append(" where jobName like ? ");
        sbSQL.append(" or jobCode like ?");

        paramsList.add("%" + name + "%");
        paramsList.add("%" + name + "%");

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

        sbSQL.append("select count(1) from Station");

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

    public List<Station> pager(Long pageNum, Long pageSize) {
        List<Station> list = new ArrayList<Station>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select * from Station");
        sbSQL.append(" order by jobId asc");

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
                list.add(toBean(rs));
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

        sbSQL.append(" select count(1) from Station");
        sbSQL.append(" where jobName like ? or jobCode like ? ");

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

    public List<Station> pagerByName(String name, Long pageNum, Long pageSize) {
        List<Station> bean = new ArrayList<Station>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select * from Station");
        sbSQL.append(" where jobName like ?");
        sbSQL.append(" or jobCode like ?");
        sbSQL.append(" order by jobId asc");

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
                bean.add(toBean(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return bean;
    }

    private Station toBean(ResultSet rs) {
        Station bean = new Station();

        try {
            bean.setJobId(rs.getLong("jobId"));
            bean.setJobCode(rs.getString("jobCode"));
            bean.setJobName(rs.getString("jobName"));
            bean.setDep(rs.getString("dep"));
            bean.setDirSup(rs.getString("dirSup"));
            bean.setJobCat(rs.getString("jobCat"));
            bean.setJobDes(rs.getString("jobDes"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}