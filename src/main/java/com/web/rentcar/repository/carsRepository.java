package com.web.rentcar.repository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.web.rentcar.entity.cars;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public  interface carsRepository extends PagingAndSortingRepository<cars,Long> {
    @Query(value = "select c from cars c"+
             "     where upper(c.type)=coalesce(upper(:type),upper(c.type))"+
                  "and upper(c.brand)=coalesce(upper(:brand),upper(c.brand))"+
                    "and c.cost between coalesce(:costMin,c.cost) and coalesce(:costMax,c.cost) ")
    Page<cars> findAllByType(@Param("type") String type,
                            @Param("brand") String brand,
                            int costMin, int costMax,
                            Pageable pageable);
    Page<cars> findAll(Pageable pageable);
    cars findById(long id);
    @Query(value = "select c from cars c "+
                    "where c.name like %?1%")
    Page<cars> findAllByName(@Param("name") String name,Pageable pageable);

}
