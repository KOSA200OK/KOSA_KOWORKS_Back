package com.my.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.department.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
