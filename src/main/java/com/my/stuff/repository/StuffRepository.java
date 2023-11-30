package com.my.stuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.my.stuff.entity.StuffEntity;


@Repository
public interface StuffRepository extends JpaRepository<StuffEntity, String> {
	
}
