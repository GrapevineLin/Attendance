package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.RepairCard;
import edu.dao.RepairCardDao;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairCardDaoImpl implements RepairCardDao {
    private RepairCard toBean(ResultSet rs) {
        RepairCard bean = new RepairCard();
        try {
            bean.setRepairId(rs.getLong("repairId"));
            bean.setEmpCode(rs.getString("empCode"));
            bean.setDate(rs.getTimestamp("date"));
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
    public Long delete(Long id){
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
        return null;
    }

    @Override
    public RepairCard load(Long id) {
        return null;
    }

    @Override
    public RepairCard loadByName(String name) {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public List<RepairCard> pager(Long pageNum, Long pageSize) {
        return null;
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
