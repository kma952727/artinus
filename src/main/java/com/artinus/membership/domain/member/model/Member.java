package com.artinus.membership.domain.member.model;

import com.artinus.membership.common.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Column(name = "phone_number", unique = true)
    @Comment("사용자의 휴대폰 번호")
    @Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
