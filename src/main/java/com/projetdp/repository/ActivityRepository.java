package com.projetdp.repository;

import com.projetdp.model.Activity;
import com.projetdp.model.Location;
import com.projetdp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ActivityRepository  extends JpaRepository<Activity,Integer> {
    /*
    String HAVERSINE_FORMULA = "6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) *" +
            " cos(radians(a.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(a.latitude)))";

    @Query("SELECT a FROM Activity a WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY "+ HAVERSINE_FORMULA + " DESC")
    List<Activity> findActivitiesWithinDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distanceWithInKM);
    */
    List<Activity> findAllByUser(User user);
    List<Activity> findAllByUserAndStartBetween(User user, Date start, Date end);
    List<Activity> findAllByLocationAndUserNot(Location location, User user);
    List<Activity> findAllByLocationAndUserNotAndStartBetween(Location location, User user, Date start, Date end);
    List<Activity> findAllByOrderByStartDesc();
    Activity getById(long id);
}
