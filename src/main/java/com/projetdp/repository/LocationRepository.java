package com.projetdp.repository;

import com.projetdp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface LocationRepository  extends JpaRepository<Location,Integer> {
    List<Location> findByCity(String city);
    List<Location> findByCityOrName(String city, String name);
    Location getByAddress(String address);
    Location getByName(String name);
    Location getById(long id);
}
