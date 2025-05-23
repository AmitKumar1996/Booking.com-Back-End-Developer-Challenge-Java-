package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.CityRepository;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotel")
public class HotelController {

  private final HotelService hotelService;
  private final HotelRepository hotelRepository;
  private final CityRepository cityRepository;

  @Autowired
  public HotelController(HotelService hotelService, HotelRepository hotelRepository, CityRepository cityRepository) {
    this.hotelService = hotelService;
    this.hotelRepository = hotelRepository;
    this.cityRepository = cityRepository;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
    Optional<Hotel> hotel = hotelRepository.findByIdAndDeletedFalse(id);
    return hotel.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHotelById(@PathVariable Long id) {
    Optional<Hotel> hotelOpt = hotelRepository.findByIdAndDeletedFalse(id);
    if (hotelOpt.isPresent()) {
      Hotel hotel = hotelOpt.get();
      hotel.setDeleted(true);
      hotelRepository.save(hotel);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/search/{cityId}")
  public ResponseEntity<List<Hotel>> getTop3ClosestHotels(@PathVariable Long cityId) {
    Optional<City> cityOpt = cityRepository.findById(cityId);
    if (!cityOpt.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    City city = cityOpt.get();
    List<Hotel> hotels = hotelRepository.findByCityIdAndDeletedFalse(cityId);

    hotels.forEach(hotel -> {
      double distance = calculateDistance(
              city.getCityCentreLatitude(), city.getCityCentreLongitude(),
              hotel.getLatitude(), hotel.getLongitude());
      hotel.setDistanceFromCityCentre(distance);
    });

    List<Hotel> top3Hotels = hotels.stream()
            .sorted(Comparator.comparingDouble(Hotel::getDistanceFromCityCentre))
            .limit(3)
            .collect(Collectors.toList());

    return ResponseEntity.ok(top3Hotels);
  }

  private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    final int R = 6371; // km
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }
}
