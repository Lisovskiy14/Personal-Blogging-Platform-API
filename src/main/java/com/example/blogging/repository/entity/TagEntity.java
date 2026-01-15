package com.example.blogging.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tags")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq_gen")
    @SequenceGenerator(
            name = "tags_seq_gen",
            sequenceName = "tags_seq"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
}
