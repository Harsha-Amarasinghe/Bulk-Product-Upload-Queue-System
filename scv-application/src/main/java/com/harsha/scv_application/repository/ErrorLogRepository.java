package com.harsha.scv_application.repository;

import com.harsha.scv_application.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
