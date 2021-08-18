package com.pro1.pro.common.service;

import com.pro1.pro.common.domain.LoginLog;

import java.util.List;

public interface LoginLogService {
    //로그인 로깅처리
    public void register(LoginLog loginLog) throws Exception;

    public List<LoginLog> list() throws Exception;

}
