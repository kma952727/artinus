package com.artinus.membership.domain.subscription.repository;

import com.artinus.membership.domain.subscription.model.Channel;
import com.artinus.membership.domain.subscription.model.vo.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelJpaRepository extends JpaRepository<Channel, Long> { }
