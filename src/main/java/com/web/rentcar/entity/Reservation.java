package com.web.rentcar.entity;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne()
    @JoinColumn(name="user_id")
    private Users user;

    @ManyToOne()
    @JoinColumn(name="car_id")
    private cars car;

    @Embedded
    @Valid
    private ReservationDates dates=new ReservationDates();


    @Column(nullable = false)
    private LocalDateTime createdTime=LocalDateTime.now();

    @Column(nullable = false)
    private String status="Đang chờ xác nhận";

    @Column(nullable = false)
    private String phonenum;

    public Reservation(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public cars getCar() {
        return car;
    }

    public void setCar(cars car) {
        this.car = car;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ReservationDates getDates() {
        return dates;
    }

    public void setDates(ReservationDates dates) {
        this.dates = dates;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public boolean hasUser(){
        return true;
    }
    public int getChargeableLateCheckoutFee() {
        return dates.isLateCheckOut() ? car.getLateCheckOutFee() :0;
    }
    public long getTotalCarCost(){
        long days= dates.totalNights();
        if (days==0)
            return 0;
        return car.getCost()*days;
    }
    public String getTotalCarCostWithLateCheckOutFee(){

        return car.setCostString(getTotalCarCost()+getChargeableLateCheckoutFee());
    }
    public String getTotalCost(){
        return car.setCostString(getTotalCarCost()+50000+100000);
    }

}
