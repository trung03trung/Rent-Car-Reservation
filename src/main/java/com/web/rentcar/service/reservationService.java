package com.web.rentcar.service;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.reservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class reservationService {
    @Autowired
    private reservationRepository reservationRepo;

    public Set<cars> sortedReservation(){
        List<Reservation> reservations=reservationRepo.findAll();

        Set<cars> setCar=new HashSet<>();
        Map<cars,Integer> mapCar=new HashMap<>();
        for(Reservation i:reservations){
            if(i.isPayment()) {
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
        return carSorted.keySet();
    }
    public Map<cars,Integer> sortedReservationMap(){
        List<Reservation> reservations=reservationRepo.findAll();

        Set<cars> setCar=new HashSet<>();
        Map<cars,Integer> mapCar=new HashMap<>();
        for(Reservation i:reservations){
            if(i.isPayment()) {
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
        return carSorted;
    }
}
