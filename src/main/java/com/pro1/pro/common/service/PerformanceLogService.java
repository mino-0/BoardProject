package com.pro1.pro.common.service;

import com.pro1.pro.common.domain.PerformanceLog;

import java.util.List;

public interface PerformanceLogService {
    public void register(PerformanceLog performanceLog) throws Exception;

    public List<PerformanceLog> list() throws Exception;
}
