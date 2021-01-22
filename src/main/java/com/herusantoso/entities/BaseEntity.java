package com.herusantoso.entities;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@MappedSuperclass
@DynamicUpdate
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -7369920601847524273L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SECURE_ID", unique = true, length = 36)
    private String secureId;

    @Column(name = "DATE_CREATED", updatable = false)
    private Instant creationDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "DATE_MODIFIED")
    private Instant modificationDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Version
    @Column(name = "VERSION")
    private Integer version = 0;

    @Column(name = "DELETED")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.secureId = UUID.randomUUID().toString();
        this.deleted = false;
        this.creationDate = Instant.now();
        this.modificationDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.modificationDate = Instant.now();
    }

}
