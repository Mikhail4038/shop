package com.keiko.authservice.event.listener;

import com.keiko.authservice.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.sql.Timestamp;
import java.time.Instant;

public class TimeEntityListener {

    @PrePersist
    public void onPersist (Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            baseEntity.setCreated (now ());
            baseEntity.setModified (now ());
        }
    }

    @PreUpdate
    public void onUpdate (Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            baseEntity.setModified (now ());
        }
    }

    private Timestamp now () {
        return Timestamp.from (Instant.now ());
    }
}
