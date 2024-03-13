package com.keiko.userservice.entity;

import com.keiko.commonservice.entity.BaseEntity;
import com.keiko.commonservice.listener.TimeEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity (name = "t_user")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class User extends BaseEntity {

    @Email
    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String password;

    private String name;
    private boolean enabled;

    @ManyToMany (fetch = LAZY, cascade = {MERGE})
    private Set<Role> roles;

    @OneToOne (cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    private UserAddress userAddress;

    public User (Long id, LocalDateTime created, LocalDateTime modified, @Email String email, String password, String name, Set<Role> roles, UserAddress userAddress) {
        super (id, created, modified);
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
        this.userAddress = userAddress;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        User user = (User) o;

        return new EqualsBuilder ()
                .append (email, user.email)
                .append (password, user.password)
                .append (name, user.name)
                .append (userAddress, user.getUserAddress ())
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (getId ())
                .append (email)
                .append (password)
                .append (name)
                .append (userAddress)
                .toHashCode ();
    }
}
