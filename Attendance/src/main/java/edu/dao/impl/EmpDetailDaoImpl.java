package edu.dao.impl;

import com.liuvei.common.DbFun;
import edu.bean.EmpDetail;
import edu.dao.EmpDetailDao;
import edu.ui.ctrl.UIConst;
import edu.util.DbUtil.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDetailDaoImpl implements EmpDetailDao {
    @Override
    public EmpDetail getDetail(String name) {
        EmpDetail empDetail = new EmpDetail();

        StringBuffer sb = new StringBuffer();
        List<Object> paramsList = new ArrayList<>();

        sb.append("select S.*, D.* from employee S left join station D on S.jobId = D.jobId where empCode = ?");

        paramsList.add(name);

        String sql = sb.toString();
        Object[] params = paramsList.toArray();

        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConn();
            rs = DbFun.query(conn, sql, params);
            if(rs.next()){
                empDetail = toBean(rs);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return empDetail;
    }

    private EmpDetail toBean(ResultSet rs){
        EmpDetail empDetail = new EmpDetail();
        try {
            empDetail.setEmpName(rs.getString("empName"));
            empDetail.setEmpCode(rs.getString("empCode"));
            empDetail.setSex(rs.getString("sex"));
            empDetail.setDepName(rs.getString("dep"));
            empDetail.setJobName(rs.getString("jobName"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empDetail;
    }
}
