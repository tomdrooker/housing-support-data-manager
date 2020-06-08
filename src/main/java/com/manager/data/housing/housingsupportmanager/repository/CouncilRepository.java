package com.manager.data.housing.housingsupportmanager.repository;

import com.manager.data.housing.housingsupportmanager.model.Council;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilRepository extends JpaRepository<Council, Long> {
}