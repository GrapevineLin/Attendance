package edu.service.impl;

import edu.bean.CheckReport;

import java.util.List;

public interface CheckReportService {

    List<CheckReport> list();

    Long count();
}
