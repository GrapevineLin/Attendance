package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.RepairCard;
import edu.dao.RepairCardDao;
import edu.util.DbUtil.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairCardDaoImpl implements RepairCardDao {
    private RepairCard toBean(ResultSet rs) {
        RepairCard bean = new RepairCard();
        try {
            bean.setRepairId(rs.getLong("repairId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setDate(rs.getDate("date"));
            bean.setReason(rs.getString("reason"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return bean;
    }

    @Override
    public List<RepairCard> list() {
        List<RepairCard> list = new ArrayList<RepairCard>();
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        sbSQL.append("select * from RepairCard");
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
    public Long insert(RepairCard bean) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        Long result = 0L;
        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();
        // SQL语句
        sbSQL.append(" delete from RepairCard");
        sbSQL.append(" where repairId=?");
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

    @Override
    public Long update(RepairCard bean) {
        Connection con = DbFun.getConn();
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement("update RepairCard set empCode=? ,date=?,reason=? where repairId=?");
            pstm.setString(1, bean.getEmpCode());
            pstm.setDate(2, bean.getDate());
            pstm.setString(3, bean.getReason());
            pstm.setLong(4, bean.getRepairId());
            //设置完参数就执行返回结果
            int rs = pstm.executeUpdate();
            System.out.println("rs:"+rs);
            return Integer.toUnsignedLong(rs);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    @Override
    public RepairCard load(Long id) {
        Connection con = DbUtil.getConn();
        PreparedStatement pstm = null;
        RepairCard repairCard = new RepairCard();
        try {
            pstm = con.prepareStatement("select * from repairCard where repairId=?");
            pstm.setLong(1, id);
            //执行查询
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                repairCard.setRepairId(rs.getLong(1));
                repairCard.setEmpCode(rs.getString(2));
                repairCard.setDate(rs.getDate(3));
                repairCard.setReason(rs.getString(4));
                return repairCard;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RepairCard loadByName(String name) {
        return null;
    }

    @Override
    public Long count() {
        Long result = 0L;

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append("select count(1) from RepairCard");

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
    public List<RepairCard> pager(Long pageNum, Long pageSize) {
        List<RepairCard> list = new ArrayList<RepairCard>();

        StringBuffer sbSQL = new StringBuffer();
        List<Object> paramsList = new ArrayList<Object>();

        sbSQL.append(" select * from RepairCard");
        sbSQL.append(" order by repairId asc");

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

    @Override
    public Long countByName(String name) {
        return null;
    }

    @Override
    public List<RepairCard> pagerByName(String name, Long pageNum, Long pageSize) {
        return null;
    }
}
