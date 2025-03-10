package com.artinus.membership.common.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
abstract public class BaseEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    @Comment("생성 날짜")
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    @Comment("갱신 날짜")
    @Column(name = "updated_at", insertable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    public void PrePersist(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void PreUpdate(){
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        var other = (BaseEntity) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}