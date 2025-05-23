package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


  private HotelService hotelService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getHotelById(@PathVariable Integer id) {
    return hotelService.getHotelById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }


  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/ready")
  public ResponseEntity<String> ready() {
    return ResponseEntity.ok().build();
  }
}
