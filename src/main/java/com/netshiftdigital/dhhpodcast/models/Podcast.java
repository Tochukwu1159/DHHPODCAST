package com.netshiftdigital.dhhpodcast.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "podcast")
@Entity
@Builder
public class Podcast extends BaseEntity{
    private String title;

    // enum of plans

    private String coverPhoto;

    private  String description;

    private  boolean isFavorite = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "podcast_id")
    private List<Episodes> episodes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PodcastCategory category;


//

}
