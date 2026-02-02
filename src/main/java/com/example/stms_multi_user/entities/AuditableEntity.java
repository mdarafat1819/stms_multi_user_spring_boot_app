package com.example.stms_multi_user.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter 
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
    
    @CreatedDate
    private LocalDateTime createdDAte;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @LastModifiedBy
    private String updatedBy;
}
