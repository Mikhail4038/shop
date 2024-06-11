package com.keiko.productservice.event.listener;

import com.keiko.productservice.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class TimeEntityListener {

    @PrePersist
    public void onPersist (Object entity) {
        if (entity instanceof BaseEntity baseEntity) {
            baseEntity.setCreated (now ());
            baseEntity.setModified (now ());
        }
    }

    @PreUpdate
    public void onUpdate (Object entity) {
        if (entity instanceof BaseEntity baseEntity) {

            baseEntity.setModified (now ());
        }
    }

    private LocalDateTime now () {
        return LocalDateTime.now ();
    }
}
