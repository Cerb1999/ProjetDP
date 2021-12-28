package com.projetdp.service;


import com.projetdp.model.Location;
import com.projetdp.repository.LocationRepository;
import com.projetdp.request.LocationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> listLocationsByCity(String city) {
        return locationRepository.findByCity(city);
    }

    public List<Location> listLocationsByCityOrName(String city, String name) {
        return locationRepository.findByCityOrName(city, name);
    }

    public void addLocation(LocationRequest request) {
        locationRepository.save(new Location(
                request.getName(),
                request.getAddress(),
                request.getCity(),
                request.getLatitude(),
                request.getLongitude()
                ));
    }

    public Location getLocationByAddress(String address) {
        return locationRepository.getByAddress(address);
    }
}
