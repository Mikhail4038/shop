package com.keiko.shopservice.listener;

import com.keiko.shopservice.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

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

    private LocalDateTime now () {
        return LocalDateTime.now ();
    }
}
