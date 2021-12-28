package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.model.Location;
import com.projetdp.request.LocationRequest;
import com.projetdp.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private static final Gson gson = new Gson();

    @GetMapping("/user/location/get/bycityorname")
    public @ResponseBody List<Location> locationSubmit(@RequestParam("city") String city, @RequestParam("name") String name) {
        return locationService.listLocationsByCityOrName(city, name);
    }

    @PostMapping("user/location/add")
    public ResponseEntity<?> addLocation(@RequestBody LocationRequest request) throws NullPointerException{
        locationService.addLocation(request);
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }
}