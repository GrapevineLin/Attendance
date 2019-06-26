package edu.dao;

import edu.bean.Classes;

import java.util.List;

public interface ClassesDao {
    List<Classes> list();

    Long insert(Classes bean);

    Long delete(Long id);

    Long update(Classes bean);

    Classes load(Long id);

    Classes loadByName(String name);

    Classes loadByCode(String code);

    Long count();

    List<Classes> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<Classes> pagerByName(String name, Long pageNum, Long pageSize);
}
