package com.pro1.pro.repository;

import com.pro1.pro.domain.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {
}
