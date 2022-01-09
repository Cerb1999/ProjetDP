package com.projetdp.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"activities"})
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    private double latitude;
    private double longitude;

    @OneToMany(targetEntity = Activity.class, mappedBy = "location", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Activity> activities = new HashSet<>();

    public Location(String name, String address, String city, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}