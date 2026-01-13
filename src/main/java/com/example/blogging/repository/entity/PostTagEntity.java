package com.example.blogging.repository.entity;

import com.example.blogging.common.PostTagId;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "posts_tags")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostTagEntity {

    @EmbeddedId
    private PostTagId id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private TagEntity tag;
}
