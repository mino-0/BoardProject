package com.pro1.pro.common.repository;

import com.pro1.pro.common.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

}
