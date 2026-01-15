package com.example.blogging.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "posts_tags")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_tags_seq_gen")
    @SequenceGenerator(
            name = "posts_tags_seq_gen",
            sequenceName = "posts_tags_seq"
    )
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tag;
}
