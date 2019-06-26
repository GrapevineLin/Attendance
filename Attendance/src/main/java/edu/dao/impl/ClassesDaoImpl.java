package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.Classes;
import edu.dao.ClassesDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassesDaoImpl implements ClassesDao {
    @Override
    public List<Classes> list() {
        List<Classes> list = new ArrayList<Classes>();

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sb.append("select * from classes");
        //添加参数

        //转换类型
        String sql = sb.toString();
//        Object[] params = paramsList.toArray();
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{

            conn = DbUtil.getConn();

//            rs = DbFun.query(conn, sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
//                Member member = new Member();
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
    public Long insert(Classes bean) {
        Long result = 0l;

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("insert into classes");
        sb.append("(");
        sb.append("classCode, className, am, pm, remark");
        sb.append(")");
        sb.append(" values(?,?,?,?,?)");
        //添加参数
        paramsList.add(bean.getClassCode());
        paramsList.add(bean.getClassName());
        paramsList.add(bean.getAm());
        paramsList.add(bean.getPm());
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
    public Long delete(Long id) {
        Long result = 0L;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合SQL
        sb.append("delete from classes where classId=?");
        //添加参数
        paramsList.add(id);

        //类型转换
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try{
            conn = DbUtil.getConn();

            result = DbFun.update(conn, sql, params);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public Long update(Classes bean) {
        Long result = 0L;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //组合sql
        sb.append("update classes");
        sb.append(" set classCode=?, className=?, am=?, pm=?, remark=?");
        sb.append(" where classId=?");
        //添加参数
        paramsList.add(bean.getClassCode());
        paramsList.add(bean.getClassName());
        paramsList.add(bean.getAm());
        paramsList.add(bean.getPm());
        paramsList.add(bean.getRemark());
        paramsList.add(bean.getClassId());


        //类型转换
        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            result = DbFun.update(conn, sql, params);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            DbUtil.close(conn);
        }
        return result;
    }

    @Override
    public Classes load(Long id) {
           Classes member = null;

        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from classes where classId=?");
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
    public Classes loadByCode(String code) {
        Classes member = null;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from classes where classCode=?");
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
    public Classes loadByName(String name) {
        Classes member = null;
        //定义变量
        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        //组合sql
        sb.append("select * from classes where className=?");
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
        sb.append("select count(*) from classes");
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
    public List<Classes> pager(Long pageNum, Long pageSize) {
        List<Classes> list = new ArrayList<Classes>();

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sb.append("select * from classes");

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
    public Long countByName(String name) {
        Long result = null;

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sb.append("select count(1) from classes where className=?");
        paramsList.add(name);

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
    public List<Classes> pagerByName(String name, Long pageNum, Long pageSize) {
        List<Classes> list = new ArrayList<Classes>();

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        //模糊查询：使用like 和 %
        sb.append("select * from classes where className like ?");
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
        name = "%" + name + "%";
        paramsList.add(name);

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

    private Classes toBean(ResultSet rs){
        Classes bean = new Classes();

        try{
            bean.setClassId(rs.getLong("classId"));
            bean.setClassCode(rs.getString("classCode"));
            bean.setClassName(rs.getString("className"));
            bean.setAm(rs.getTime("am"));
            bean.setPm(rs.getTime("pm"));
            bean.setRemark(rs.getString("remark"));
            System.out.println(bean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return bean;
    }

}
