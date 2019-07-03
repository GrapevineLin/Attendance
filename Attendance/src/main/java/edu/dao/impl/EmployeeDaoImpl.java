package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Employee;
import edu.dao.EmployeeDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public List<Employee> list() {
        List<Employee> list = new ArrayList<Employee>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select * from Employee");

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
    public Long insert(Employee bean) {
        // TODO Auto-generated method stub
        Long result = 0L;

        // 0)定义变量
        StringBuffer sql = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1)组合SQL
        sql.append(" Insert Into Employee");
        sql.append(" (");
        sql.append(" empCode,empName,sex,age");
        sql.append(" ,nation,IDC,money,tel");
        sql.append(" ,ecp,ecpTel,jobId,des");
        sql.append(" )");
        sql.append(" values(?,?,?,? ,?,?,?,? ,?,?,?,? )");

        // 2)添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add(bean.getEmpName());
        paramsList.add(bean.getSex());
        paramsList.add(bean.getAge());

        paramsList.add(bean.getNation());
        paramsList.add(bean.getIDC());
        paramsList.add(bean.getMoney());
        paramsList.add(bean.getTel());

        paramsList.add(bean.getEcp());
        paramsList.add(bean.getEcpTel());
        paramsList.add(bean.getJobId());
        paramsList.add(bean.getDes());


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
        // TODO Auto-generated method stub

        Long result = 0L;

        // 0）定义变量
        StringBuffer sql = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1) 组合SQL
        sql.append(" delete from Employee");
        sql.append(" where empId=?");

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
    public Long update(Employee bean) {
        // TODO Auto-generated method stub

        Long result = 0L;

        // 0) 定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        // 1) 组合SQL
        sbSQL.append(" update Employee ");
        sbSQL.append(" set empCode=?, empName=?, sex=?, age=?");
        sbSQL.append(" , nation=?, IDC=?, money=?, tel=?");
        sbSQL.append(" , ecp=?, ecpTel=?, jobId=?, des=?");
        sbSQL.append(" where empId=?");

        // 2) 添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add(bean.getEmpName());
        paramsList.add(bean.getSex());
        paramsList.add(bean.getAge());

        paramsList.add(bean.getNation());
        paramsList.add(bean.getIDC());
        paramsList.add(bean.getMoney());
        paramsList.add(bean.getTel());

        paramsList.add(bean.getEcp());
        paramsList.add(bean.getEcpTel());
        paramsList.add(bean.getJobId());
        paramsList.add(bean.getDes());

        paramsList.add(bean.getEmpId());

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
    public Employee load(Long id) {
        Employee bean = null;
        //定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sbSQL.append("select E.*,S.jobName from Employee E");
        sbSQL.append(" left join Station S on E.jobId=S.jobId");
        sbSQL.append(" where E.empId=?");

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
                bean =toBeanEx(rs);
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
    public Employee loadByName(String name) {
        Employee bean = null;
        //定义变量
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sbSQL.append("select * from Employee ");
        sbSQL.append("where empName like ? or empCode like ?");
        sbSQL.append(" order by empId asc");

        //添加参数
        paramsList.add("%"+name+"%");
        paramsList.add("%"+name+"%");

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
        }finally{
            //关闭
            DbUtil.close(conn);
        }
        return bean;
    }

    @Override
    public Long count() {
        Long result =0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();

        sbSQL.append("select count(1) From Employee");

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
    public List<Employee> pager(Long pageNum, Long pageSize) {
        List<Employee> list = new  ArrayList<Employee>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();

        sbSQL.append("select E.*,S.jobName from Employee E");
        sbSQL.append(" left join Station S on E.jobId=S.jobId");
        sbSQL.append(" order by E.empId asc");

        if(pageNum<1){
            pageNum=1L;
        }
        if(pageSize<1){
            pageSize=10L;
        }
        Long startIndex = (pageNum - 1)*pageSize;
        sbSQL.append(" limit "+startIndex+","+pageSize);

        String sql =sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            while (rs.next()) {
                list.add(toBeanEx(rs));

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
    public Long countByName(String name) {
        Long result =0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();

        sbSQL.append("select count(1) From Employee");
        sbSQL.append(" where empName like ? ");

        name = "%" + name + "%";
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
    public List<Employee> pagerByName(String name, Long pageNum, Long pageSize) {
        List<Employee> list = new  ArrayList<Employee>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new  ArrayList<Object>();

        sbSQL.append("select E.*,S.jobName from Employee E");
        sbSQL.append(" left join Station S on E.jobId=S.jobId");
        sbSQL.append(" where E.empName like ?");
        sbSQL.append(" order by E.empId asc");

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

        String sql =sbSQL.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql, params);

            while (rs.next()) {
                list.add(toBeanEx(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }
        return list;
    }
    private Employee toBean(ResultSet rs) {
        Employee bean = new Employee();

        try {
            bean.setEmpId(rs.getLong("empId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setEmpName(rs.getString("empName"));
            bean.setSex(rs.getString("sex"));
            bean.setAge(rs.getLong("age"));
            bean.setNation(rs.getString("nation"));
            bean.setIDC(rs.getString("IDC"));
            bean.setMoney(rs.getLong("money"));
            bean.setTel(rs.getString("tel"));
            bean.setEcp(rs.getString("ecp"));
            bean.setEcpTel(rs.getString("ecpTel"));
            bean.setJobId(rs.getLong("jobId"));
            bean.setDes(rs.getString("des"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
    private Employee toBeanEx(ResultSet rs) {
        Employee bean = new Employee();

        try {
            bean.setEmpId(rs.getLong("empId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setEmpName(rs.getString("empName"));
            bean.setSex(rs.getString("sex"));
            bean.setAge(rs.getLong("age"));
            bean.setNation(rs.getString("nation"));
            bean.setIDC(rs.getString("IDC"));
            bean.setMoney(rs.getLong("money"));
            bean.setTel(rs.getString("tel"));
            bean.setEcp(rs.getString("ecp"));
            bean.setEcpTel(rs.getString("ecpTel"));
            bean.setJobId(rs.getLong("jobId"));
            bean.setDes(rs.getString("des"));
            bean.setJobName(rs.getString("jobName"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bean;
    }
}
