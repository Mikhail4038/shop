package com.keiko.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table (name = "t_role")
public class Role extends BaseEntity {

    private String name;

    @ManyToMany (mappedBy = "roles", fetch = LAZY)
    private Set<User> users;
}
