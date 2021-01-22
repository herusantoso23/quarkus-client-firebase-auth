package com.herusantoso.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Where(clause = "deleted='false'")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "photo_url", length = 1000)
    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<Role> roles;

    @Override
    @PrePersist
    public void prePersist(){
        super.prePersist();
    }

}
