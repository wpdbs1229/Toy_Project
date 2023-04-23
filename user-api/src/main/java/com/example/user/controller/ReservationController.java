package com.example.user.controller;

import com.example.user.domain.dto.ReservationDto;
import com.example.user.domain.entity.ReservationEntity;
import com.example.user.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    @PostMapping("/")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> saveReservation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestBody ReservationDto.Request request){
        var response = reservationService.saveReservation(request,dateTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> readSCustomerReservation(
            @RequestParam String phone,
            final Pageable pageable
    ){
        Page<ReservationEntity> allCustomerReservation
                = reservationService.getAllCustomerReservation(phone, pageable);
        return ResponseEntity.ok(allCustomerReservation);
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> readManagerReservation(
            @RequestParam String storeCode,
            final Pageable pageable
    ){
        Page<ReservationEntity> allManagerReservation
                = reservationService.getAllManagerReservation(storeCode, pageable);
        return ResponseEntity.ok(allManagerReservation);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> readReservationDetail(@RequestParam Long reservationId){
       var reservation = reservationService.readReservationDetail(reservationId);
       return ResponseEntity.ok(reservation);
    }

    @PutMapping("/adjustment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateReservation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam Long reservationId
    ){
        var reservation = reservationService.updateReservation(dateTime,reservationId);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteReservation(@RequestParam Long reservationId){
        var reservation = reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> approveReservation(@RequestBody ReservationDto.ApproveRequest request){
        var confirm = reservationService.approveReservation(request);
        return ResponseEntity.ok(confirm);
    }

    @PostMapping("/check")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> checkReservation(@RequestBody ReservationDto.CheckReservation request){
        var reservation = reservationService.checkReservation(request);
        return ResponseEntity.ok(reservation);
    }
}
