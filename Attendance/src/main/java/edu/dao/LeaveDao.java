package edu.dao;

import edu.bean.Leave;

import java.util.List;

public interface LeaveDao {
    List<Leave> list();

    Long insert(Leave bean);

    Long delete(Long id);

    Long update(Leave bean);

    Leave load(Long id);

    Leave loadByName(String name);

    Long count();

    List<Leave> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<Leave> pagerByName(String name, Long pageNum, Long pageSize);
}
