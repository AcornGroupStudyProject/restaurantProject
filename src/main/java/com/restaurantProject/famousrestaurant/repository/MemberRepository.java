package com.restaurantProject.famousrestaurant.repository;

import com.restaurantProject.famousrestaurant.dto.Member;
import com.restaurantProject.famousrestaurant.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByMemberId(String memberId);
}
