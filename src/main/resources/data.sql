insert into channel (name, channel_type, created_at, updated_at) values
    ('HOME_PAGE', 10, NOW(), null)
    ,('MOBILE_APP', 10, NOW(), null)
    ,('NAVER', 10, NOW(), null)
    ,('SKT', 10, NOW(), null)
    ,('KT', 10, NOW(), null)
    ,('LGU', 10, NOW(), null)

    ,('HOME_PAGE', 30, NOW(), null)
    ,('MOBILE_APP', 30, NOW(), null)
    ,('CALL_CENTER', 30, NOW(), null)
    ,('CHAT', 30, NOW(), null)
    ,('MAIL', 30, NOW(), null);

insert into plan (name, is_default, channel_id, subscribe_state, period, created_at, updated_at) values
    ('프리미엄2 구독', true, 1, 780, 1, NOW(), null)
    ,('프리미엄2 구독', true, 2, 780, 1, NOW(), null)
    ,('이벤트 노말 구독', true, 3, 350, 1, NOW(), null)
    ,('이벤트 노말 구독', true, 4, 350, 1, NOW(), null)
    ,('기본 구독', true, 5, 10, 1, NOW(), null)
    ,('기본 구독', true, 6, 10, 1, NOW(), null)

    ,('일반 구독', true, 7, 10, 1, NOW(), null)
    ,('일반 구독', true, 8, 350, 1, NOW(), null)
    ,('일반 구독', true, 9, 350, 1, NOW(), null)
    ,('일반 구독', true, 10, 350, 1, NOW(), null)
    ,('일반 구독', true, 11, 350, 1, NOW(), null);

insert into member (phone_number, created_at, updated_at) values
    ('010-9991-1493', NOW(), null)
    ,('010-1515-9120', NOW(), null);

insert subscription (member_id, channel_id, plan_id, created_at, updated_at) values
    (1, 1, 1, NOW(), null)
    ,(2, 1, 1, NOW(), null);