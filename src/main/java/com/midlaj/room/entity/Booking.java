package com.midlaj.room.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    private String id;
    private Long userId;
    private Long resortId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Date bookedDate;
    private Double totalPrice;
    private PaymentStatus paymentStatus;
}
