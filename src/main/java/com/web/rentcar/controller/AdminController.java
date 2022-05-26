package com.web.rentcar.controller;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.Users;
import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.carsRepository;
import com.web.rentcar.repository.reservationRepository;
import com.web.rentcar.repository.usersRepository;
import com.web.rentcar.service.carsService;
import com.web.rentcar.service.reservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    private reservationRepository reservationRepo;

    @Autowired
    private usersRepository usersRepo;

    @Autowired
    private carsService carsService;

    @Autowired
    private reservationService reservationService;

    @Autowired
    private carsRepository carsRepo;
    private cars cars;
    @GetMapping("/admin")
    public String Dashboard(Model model){
        List<Reservation> reservations=reservationRepo.findAll();
        long sum=0;
        int quantity=0;
        for(Reservation i:reservations){
            if(i.getDates().getCheckOutDate().getMonthValue()== LocalDateTime.now().getMonthValue()&&i.isPayment())
                sum+=i.getTotalCostInt();
                quantity+=1;
        }
        long total=0;
        for(Reservation i:reservations){
            if(i.isPayment()) {
                total += i.getTotalCostInt();
            }
        }

        List<Users> users=usersRepo.findAll();
        String sumString= carsService.setCostString(sum);
        String totalString=carsService.setCostString(total);
        model.addAttribute("sumOfMonth",sumString);
        model.addAttribute("quantity",quantity);
        model.addAttribute("numberUser",users.size());
        model.addAttribute("total",totalString);
        model.addAttribute("keySetCars",reservationService.sortedReservation());
        model.addAttribute("mapCars",reservationService.sortedReservationMap());
        model.addAttribute("page","dashboard");
        return "admin/dashboard";
    }
    @GetMapping("/admin/tables")
    public String Table(Model model, Pageable pageable){
        Page<cars> car=carsRepo.findAll(pageable);
        List<Reservation> reservations=reservationRepo.findAll();
        List<Users> user=usersRepo.findAll();
        model.addAttribute("cars",car.getContent());
        model.addAttribute("reservations",reservations);
        model.addAttribute("users",user);
        model.addAttribute("page","tables");
        return "admin/tables";
    }

    @GetMapping("/admin/create")
    public String Create(Model model){
        model.addAttribute("car",new cars());
        model.addAttribute("page","tables");
        return "admin/CRUD-car";
    }
    @PostMapping("/admin/save")
    public String Save(cars car, RedirectAttributes ra){
        carsRepo.save(car);
        ra.addFlashAttribute("message","Thêm thành công");
        return "redirect:/admin/tables";

    }
    @GetMapping("/admin/edit/{id}")
    public String ShowEditForm(@PathVariable("id") long id,Model model){
        cars car=carsRepo.findById(id);
        model.addAttribute("car",car);
        model.addAttribute("page","tables");
        return "admin/CRUD-car";
    }
    @GetMapping("/admin/delete/{id}")
    public String DeleteCar(@PathVariable("id") long id,RedirectAttributes ra){
        carsRepo.deleteById(id);
        ra.addFlashAttribute("message","Xoá thành công");
        return "redirect:/admin/tables";

    }
    @GetMapping("/admin/orders")
    public String Orders(Model model){
        List<Reservation> allReservations=reservationRepo.findAll();
        List<Reservation> reservations=new ArrayList<>();
        for(Reservation i:allReservations){
            if(i.isPayment()==false||i.isStatus()==false)
                reservations.add(i);
        }
        model.addAttribute("page","orders");
        model.addAttribute("reservations",reservations);
        return "admin/order.html";


    }
    @GetMapping("/admin/reservation/{id}")
    public String ShowEditFormReservation(@PathVariable("id") long id,Model model){
        Reservation reservation=reservationRepo.findById(id);
        model.addAttribute("reservation",reservation);
        model.addAttribute("page","orders");
        return "admin/CRUD-order";
    }
    @PostMapping("/admin/save-reservation")
    public String SaveOrder(Reservation reservation, RedirectAttributes ra){
        reservationRepo.save(reservation);
        ra.addFlashAttribute("message","Thêm thành công");
        return "redirect:/admin/orders";

    }
    @GetMapping("/admin/delete-order/{id}")
    public String DeleteOrder(@PathVariable("id") long id,RedirectAttributes ra){
        reservationRepo.deleteById(id);
        ra.addFlashAttribute("message","Xoá thành công");
        return "redirect:/admin/orders";

    }
    @GetMapping("/admin/delete-reservation/{id}")
    public String DeleteReservation(@PathVariable("id") long id,RedirectAttributes ra){
        reservationRepo.deleteById(id);
        ra.addFlashAttribute("message","Xoá thành công");
        return "redirect:/admin/tables";

    }
    @GetMapping("/admin/user/{id}")
    public String ShowEditFormUser(@PathVariable("id") long id,Model model){
        Users user=usersRepo.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("page","tables");
        return "admin/CRUD-user";
    }
    @PostMapping("/admin/save-user")
    public String SaveUser(Users user, RedirectAttributes ra){
        usersRepo.save(user);
        ra.addFlashAttribute("message","Thêm thành công");
        return "redirect:/admin/tables";

    }
    @GetMapping("/admin/delete-user/{id}")
    public String DeleteUser(@PathVariable("id") long id,RedirectAttributes ra){
        usersRepo.deleteById(id);
        ra.addFlashAttribute("message","Xoá thành công");
        return "redirect:/admin/tables";

    }



}
