package com.web.rentcar.repository;

import com.web.rentcar.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface reservationRepository extends CrudRepository<Reservation,Long> {
   Reservation findById(long id);
   List<Reservation> findAll();

}
