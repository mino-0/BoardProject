package com.pro1.pro.common.service;

import com.pro1.pro.common.domain.LoginLog;
import com.pro1.pro.common.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogRepository repository;
    @Override
    public void register(LoginLog loginLog) throws Exception {
        repository.save(loginLog);
    }

    @Override
    public List<LoginLog> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "logNo"));
    }
}
