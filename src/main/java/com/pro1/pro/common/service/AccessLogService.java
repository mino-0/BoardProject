package com.pro1.pro.common.service;

import com.pro1.pro.common.domain.AccessLog;

import java.util.List;

public interface AccessLogService {
    //접근 로깅처리
    public void register(AccessLog accessLog) throws Exception;

    public List<AccessLog> list() throws Exception;
}
