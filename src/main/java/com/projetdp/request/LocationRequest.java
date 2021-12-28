package com.projetdp.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationRequest {
    private String name;
    private String address;
    private String city;
    private double latitude;
    private double longitude;
}
