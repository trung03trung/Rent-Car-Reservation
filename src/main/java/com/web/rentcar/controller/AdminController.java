package com.web.rentcar.controller;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.Users;
import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.reservationRepository;
import com.web.rentcar.repository.usersRepository;
import com.web.rentcar.service.carsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    private cars cars;
    @GetMapping("/admin")
    public String Dashboard(Model model){
        List<Reservation> reservations=reservationRepo.findAll();
        long sum=0;
        int quantity=0;
        Set<cars> setCar=new HashSet<>();
        Map<cars,Integer> mapCar=new HashMap<>();
        for(Reservation i:reservations){
            if(i.getDates().getCheckOutDate().getMonthValue()== LocalDateTime.now().getMonthValue())
                sum+=i.getTotalCostInt();
                quantity+=1;
        }
        long total=0;
        for(Reservation i:reservations){
            total+=i.getTotalCostInt();
            setCar.add(i.getCar());
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

}
