package com.artinus.membership.helper;

import com.artinus.membership.domain.member.repository.MemberJpaRepository;
import com.artinus.membership.domain.subscription.repository.ChannelJpaRepository;
import com.artinus.membership.domain.subscription.repository.PlanJpaRepository;
import com.artinus.membership.domain.subscription.repository.SubscriptionHistoryJpaRepository;
import com.artinus.membership.domain.subscription.repository.SubscriptionJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest
public abstract class ServiceLayerTestTemplate {
    @Autowired protected MemberJpaRepository memberJpaRepository;
    @Autowired protected PlanJpaRepository planJpaRepository;
    @Autowired protected ChannelJpaRepository channelJpaRepository;
    @Autowired protected SubscriptionJpaRepository subscriptionJpaRepository;
    @Autowired protected SubscriptionHistoryJpaRepository subscriptionHistoryJpaRepository;

    @Autowired protected DataBaseCleanUp dataBaseCleanUp;

    @AfterEach
    public void tearDown() {
        dataBaseCleanUp.truncateAllEntity();
    }
}
