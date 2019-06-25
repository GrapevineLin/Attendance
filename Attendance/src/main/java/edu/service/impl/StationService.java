package edu.service.impl;

import edu.bean.Station;

import java.util.List;

public interface StationService {
    List<Station> list();

    Long insert(Station bean);

    Long delete(Long id);

    Long update(Station bean);

    Station load(Long id);

    Station loadByName(String name);

    Long count();

    List<Station> pager(Long pageNum, Long pageSize);

    Long countByName(String name);

    List<Station> pagerByName(String name, Long pageNum, Long pageSize);
}