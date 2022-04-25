package com.web.rentcar.controller;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.ReservationDates;
import com.web.rentcar.entity.Users;
import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.carsRepository;
import com.web.rentcar.repository.reservationRepository;
import com.web.rentcar.repository.usersRepository;
import com.web.rentcar.service.UserServiceImp;
import com.web.rentcar.service.carsService;
import com.web.rentcar.service.customUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
@SessionAttributes("reservation")
public class ReservationController {
    @Autowired
    private carsRepository carsRepository;

    @Autowired
    private usersRepository usersRepo;

    private UserServiceImp userService;

    @Autowired
    private carsService carsService;

    @Autowired
    private reservationRepository reservationRepo;

    private long carid;
    @ModelAttribute("reservation")
    public Reservation getReservation() {
        return new Reservation();
    }

    @GetMapping("/reservation/car_{id}")
    public String bookCar(Model model, @PathVariable(name = "id") long id, @ModelAttribute("reservation") Reservation reservation){
        cars car=carsRepository.findById(id);
        this.carid=id;
        model.addAttribute("car",car);
        reservation.setCar(car);
        return "reservation";
    }
    @PostMapping("/reservation/car_{id}")
    public String bookCarCheck(Model model, @AuthenticationPrincipal customUserDetails user,
                               @Valid @ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult){
        cars car=carsRepository.findById(this.carid);
        model.addAttribute("car",car);
        if(reservation.getDates().totalNights()<1)
            bindingResult.addError(new FieldError("reservation","dates.checkOutDate","Số ngày thuê xe phải ít nhất 1 ngày "));
        if (bindingResult.hasErrors()) {
            return "reservation";
        }

        reservation.setCar(car);
        reservation.setUser(user.getUser());
        reservationRepo.save(reservation);
        return "redirect:/";
    }
    @PostMapping(value = "/reservation/car",params = "prices")
    public String carCostFragment(@ModelAttribute("reservation") Reservation reservation){
            return "fragments/car-cost::carCost";

    }
    @GetMapping("/reservation/review")
    public String reservationReview(Model model){
        List<Reservation> listReservation=reservationRepo.findAll();
        model.addAttribute("listReservation",listReservation);
        return "reservation-review";
    }
    @GetMapping("/reservation/review/{id}")
    public String reservationDetail(Model model,@PathVariable(name = "id") long id){
        Reservation reservation=reservationRepo.findById(id);
        model.addAttribute("reservation",reservation);
        return "reservation-detail";
    }
}
