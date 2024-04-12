package com.jpql_query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUser propertyUser;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "guest", nullable = false)
    private int guest;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "check_in")
    private LocalDate checkIn;

}