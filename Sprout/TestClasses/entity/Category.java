package org.AmineSidki.demo.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class Category {
    @Id
    private String code;

    private String displayName;
    private boolean active;
}