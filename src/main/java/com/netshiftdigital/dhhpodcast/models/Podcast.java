package com.netshiftdigital.dhhpodcast.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "podcast")
@Entity
@Builder
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String coverPhoto;

    private LocalDateTime createdDate;

    private  String body;
    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PodcastCategory category;

    @OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "podcast")
    private List<Comment> comments;
//

}
