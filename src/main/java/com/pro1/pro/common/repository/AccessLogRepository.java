package com.pro1.pro.common.repository;

import com.pro1.pro.common.domain.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
