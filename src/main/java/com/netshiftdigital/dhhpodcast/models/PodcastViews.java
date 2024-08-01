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
@Table(name = "podcast_views")
@Entity
@Builder
public class PodcastViews extends BaseEntity{
    private int views;
    @ManyToOne
    @JoinColumn(name="podcast_id")
    private Podcast podcast;
}
