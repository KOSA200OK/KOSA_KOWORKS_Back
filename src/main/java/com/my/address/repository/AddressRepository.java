package com.my.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.member.entity.MemberEntity;

public interface AddressRepository extends JpaRepository<MemberEntity, Long> {
	List<MemberEntity> findByName(String name);

	List<MemberEntity> findByDepartment(String department);

	List<MemberEntity> findbyMemberId(String memberId);

}
