package com.herusantoso.entities;

import com.herusantoso.enums.RoleEnum;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="roles")
@Where(clause = "deleted='false'")
public class Role extends BaseEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, unique = true)
    private RoleEnum name;

}
