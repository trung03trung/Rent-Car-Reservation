package com.web.rentcar.service;

import com.web.rentcar.entity.cars;
import com.web.rentcar.repository.carsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class carsService {
    @Autowired
    private carsRepository carsRepo;

    public Page<cars> listAll(int pageNum,String sortField,String sortDir){
        Sort sort=Sort.by("name").ascending();
        Pageable pageable= PageRequest.of(pageNum-1,2, sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());
        return carsRepo.findAll(pageable);
    }

}
