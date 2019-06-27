package edu.service.impl;

import edu.bean.PunchCard;

import java.util.List;

public interface PunchCardService {
    List<PunchCard> list();

    Long insert(PunchCard bean);

    PunchCard load(Long id);

    PunchCard loadByCode(String code);

    PunchCard loadByName(String name);


    Long count();

    List<PunchCard> pager(Long pageNum, Long pageSize);

    Long countByCode(String code);

    List<PunchCard> pagerByCode(String code, Long pageNum, Long pageSize);
}
