package edu.dao;


import edu.bean.CheckReport;

import java.util.List;

public interface CheckReportDao {
    List<CheckReport> list();
    //CheckPeport load(Long id);
   // CheckPeport loadByName(String name);
    //List<CheckPeport> pager(Long pageNum, Long pageSize);
   // Long countByName(String name);

    //List<CheckPeport> pagerByName(String name, Long pageNum, Long pageSize);
}
