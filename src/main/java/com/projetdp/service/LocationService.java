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
                request.getLongitude(),
                request.getLatitude()
                ));
    }

    public Location getLocationByAddress(String address) {
        return locationRepository.getByAddress(address);
    }

    public List<Location> listAllLocations() {
        return locationRepository.findAll();
    }

    public void removeLocation(long id) {
        Location location = locationRepository.getById(id);
        locationRepository.delete(location);
    }
}
