package com.netshiftdigital.dhhpodcast.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "episodes")
@Entity
@Builder
public class Episodes extends BaseEntity{
    private String title;

    private  String description;
    private String audioUrl;
    @ManyToOne
    @JoinColumn(name = "podcast_id", nullable = false)
    private Podcast podcast;

}
