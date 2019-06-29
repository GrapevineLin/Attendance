package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.PunchCard;
import edu.dao.PunchCardDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PunchCardDaoImpl implements PunchCardDao {
    @Override
    public List<PunchCard> list() {
        List<PunchCard> list = new ArrayList<PunchCard>();

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sb.append("select * from punchCard");
        //添加参数

        //转换类型
        String sql = sb.toString();
        Connection conn = null;
        ResultSet rs = null;
        try{

            conn = DbUtil.getConn();

            rs = DbFun.query(conn, sql);

            while (rs.next()){
                list.add(toBean(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtil.close(conn);
        }
        return list;
    }

    @Override
    public Long insert(PunchCard bean) {
        Long result = 0l;

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("insert into punchCard");
        sb.append("(");
        sb.append("empCode, empName, date, remark");
        sb.append(")");
        sb.append(" values(?,?,?,?)");
        //添加参数
        paramsList.add(bean.getEmpCode());
        paramsList.add(bean.getEmpName());
        paramsList.add(bean.getDate());
        paramsList.add(bean.getRemark());



        //转换类型
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try{
            conn = DbUtil.getConn();
            Long num = DbFun.update(conn, sql, params);
            //处理结果
            if(num > 0){
                //得到上一次插入记录时自动获取的id
                sql = "select @@identity";
                result = DbFun.queryScalarLong(conn, sql);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public PunchCard load(Long id) {
        PunchCard member = null;

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from punchCard where punchId=?");
        //添加参数
        paramsList.add(id);

        //转换类型
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);

            if(rs.next()){
                member = toBean(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return member;
    }

    @Override
    public PunchCard loadByCode(String code) {
        PunchCard member = null;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from punchCard where empCode=?");
        //添加参数
        paramsList.add(code);

        //转换类型
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);

            if(rs.next()){
                member = toBean(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return member;
    }

    @Override
    public PunchCard loadByName(String name) {
        PunchCard member = null;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from punchCard where empName=?");
        //添加参数
        paramsList.add(name);

        //转换类型
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);

            if(rs.next()){
                member = toBean(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return member;
    }

    @Override
    public Long count() {
        Long result = 0L;

        //定义变量
        StringBuffer sb = new StringBuffer();
//        List<Object> paramsList = new ArrayList<Object>();

        //组合SQL
        sb.append("select count(*) from punchCard");
        //类型转换
        String sql = sb.toString();

        Connection conn = null;
        try{
            conn = DbUtil.getConn();
            result = DbFun.queryScalarLong(conn, sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }

        return result;
    }

    @Override
    public List<PunchCard> pager(Long pageNum, Long pageSize) {
        List<PunchCard> list = new ArrayList<PunchCard>();

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

//        sb.append("select * from news");
        //组合SQL将news表和newscat表结合
        sb.append("select N.*,C.empCode from punchcard N left join employee C on N.empCode = C.empCode");
//      ------------  分页！！！！！！！----------
        if(pageNum < 1){
            pageNum = 1L;
        }
        if(pageSize < 1){
            pageSize = 10L;
        }
        Long startIndex = (pageNum - 1) * pageSize; //重点！！
        sb.append(" limit " + startIndex + "," + pageSize);
//      ------------  分页！！！！！！！ ----------

        String sql = sb.toString();
        Object[] params = paramsList.toArray();
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            while (rs.next()){
                list.add(toBean(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return list;
    }

    @Override
    public Long countByCode(String code) {
        Long result = null;

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sb.append("select count(1) from punchCard where empCode=?");
        paramsList.add(code);

        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try{
            conn = DbUtil.getConn();
            result = DbFun.queryScalarLong(conn, sql, params);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public List<PunchCard> pagerByCode(String code, Long pageNum, Long pageSize) {
        List<PunchCard> list = new ArrayList<PunchCard>();

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //模糊查询：使用like 和 %
//        sb.append("select * from news where author like ?");

        //组合两张news和newscat
        sb.append("select N.*, C.empCode from punchcard N left join employee C on N.empCode=C.empCode where N.empCode like ?");
        //分页
        if(pageNum < 1) {
            pageNum = 1L;
        }
        if(pageSize < 1) {
            pageSize = 10L;
        }
        Long startIndex = (pageNum - 1) * pageSize;
        sb.append(" limit " + startIndex + "," + pageSize);
        //模糊查询
        code = "%" + code + "%";
        paramsList.add(code);

        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            while (rs.next()){
                list.add(toBean(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
//            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return list;
    }
    private PunchCard toBean(ResultSet rs){
        PunchCard bean = new PunchCard();

        try{
            bean.setPunchId(rs.getLong("punchId"));
            bean.setEmpName(rs.getString("empName"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setDate(rs.getTimestamp("date"));
            bean.setRemark(rs.getString("remark"));
            System.out.println(bean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return bean;
    }

}
