package com.web.rentcar.repository;

import com.web.rentcar.entity.Reservation;
import com.web.rentcar.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface reservationRepository extends CrudRepository<Reservation,Long> {
   Reservation findById(long id);

   @Query(value="select r from Reservation r "+
         "where r.user=?1")
   List<Reservation> findByUser(Users user);

   List<Reservation> findAll();
}
