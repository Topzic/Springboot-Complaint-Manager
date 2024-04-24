package com.ji.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComplaintHandlerRepository extends JpaRepository<ComplaintHandler, Integer> {
	
}
