package edu.dao;

import edu.bean.RepairCard;

import java.util.List;

public interface RepairCardDao {
    List<RepairCard> list();

    Long insert(RepairCard bean);

    Long delete(Long id);

    Long update(RepairCard bean);

    RepairCard load(Long id);

    RepairCard loadByName(String name);

    Long count();

    List<RepairCard> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<RepairCard> pagerByName(String name, Long pageNum, Long pageSize);

}
