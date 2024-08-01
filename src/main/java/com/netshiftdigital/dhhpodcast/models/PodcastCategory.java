package com.netshiftdigital.dhhpodcast.models;

import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "podcast_category")
@Entity
@Builder
public class PodcastCategory extends BaseEntity{

    private String name;
    private String coverPhoto;

//    private  String description;
   private String subscriptionPlan;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Podcast> podcastList;
}
