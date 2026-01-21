package com.example.blogging.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
    @SequenceGenerator(
            name = "roles_seq_gen",
            sequenceName = "roles_seq"
    )
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private RoleEntity parent;

    @Override
    public String getAuthority() {
        return name;
    }
}
