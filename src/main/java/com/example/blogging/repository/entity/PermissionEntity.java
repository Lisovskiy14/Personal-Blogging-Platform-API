package com.example.blogging.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "permissions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_seq_gen")
    @SequenceGenerator(
            name = "permissions_seq_gen",
            sequenceName = "permissions_seq"
    )
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
