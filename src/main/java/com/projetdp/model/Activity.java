package com.projetdp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"location", "user"})
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @ManyToOne
    private Location location;
    @ManyToOne
    private User user;

    public Activity(String description, Date start, Date end, Location location, User user) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.location = location;
        this.user = user;
    }
}