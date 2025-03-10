package com.artinus.membership.domain.member.repository;

import com.artinus.membership.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> { }
