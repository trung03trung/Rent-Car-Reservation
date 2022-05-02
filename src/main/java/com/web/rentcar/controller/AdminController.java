package com.web.rentcar.controller;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.Users;
import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.carsRepository;
import com.web.rentcar.repository.reservationRepository;
import com.web.rentcar.repository.usersRepository;
import com.web.rentcar.service.carsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
    private carsRepository carsRepo;
    private cars cars;
    @GetMapping("/admin")
    public String Dashboard(Model model){
        List<Reservation> reservations=reservationRepo.findAll();
        long sum=0;
        int quantity=0;
        Set<cars> setCar=new HashSet<>();
        Map<cars,Integer> mapCar=new HashMap<>();
        for(Reservation i:reservations){
            if(i.getDates().getCheckOutDate().getMonthValue()== LocalDateTime.now().getMonthValue()&&i.isPayment())
                sum+=i.getTotalCostInt();
                quantity+=1;
        }
        long total=0;
        for(Reservation i:reservations){
            if(i.isPayment()) {
                total += i.getTotalCostInt();
                setCar.add(i.getCar());
            }
        }
        int count=0;
       for(cars i:setCar){
           count=0;
           for(Reservation j:reservations){
               if(i==j.getCar())
                   count++;
           }
           mapCar.put(i,count);
       }
        Map<cars,Integer> carSorted=mapCar.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
       Set set=carSorted.keySet();
        List<Users> users=usersRepo.findAll();
        String sumString= carsService.setCostString(sum);
        String totalString=carsService.setCostString(total);
        model.addAttribute("sumOfMonth",sumString);
        model.addAttribute("quantity",quantity);
        model.addAttribute("numberUser",users.size());
        model.addAttribute("total",totalString);
        model.addAttribute("keySetCars",set);
        model.addAttribute("mapCars",carSorted);
        return "admin/dashboard";
    }
    @GetMapping("/admin/tables")
    public String Table(Model model, Pageable pageable){
        Page<cars> car=carsRepo.findAll(pageable);
        List<Reservation> reservations=reservationRepo.findAll();
        model.addAttribute("cars",car.getContent());
        model.addAttribute("reservations",reservations);
        return "admin/tables";
    }

    @GetMapping("/admin/create")
    public String Create(Model model){
        model.addAttribute("car",new cars());
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
        model.addAttribute("reservations",reservations);
        return "admin/order.html";


    }
    @GetMapping("/admin/reservation/{id}")
    public String ShowEditFormReservation(@PathVariable("id") long id,Model model){
        Reservation reservation=reservationRepo.findById(id);
        model.addAttribute("reservation",reservation);

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



}
