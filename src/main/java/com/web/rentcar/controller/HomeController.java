package com.web.rentcar.controller;

import com.web.rentcar.CustomUserDetails.CustomUserDetails;
import com.web.rentcar.entity.*;
import com.web.rentcar.repository.reservationRepository;
import com.web.rentcar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import com.web.rentcar.repository.usersRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.data.domain.Page;
import com.web.rentcar.repository.carsRepository;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private carsRepository carsRepository;

    @Autowired
    private usersRepository usersRepo;

    private  UserServiceImp userService;
    @Autowired
    private carsService carsService;

    @Autowired
    private reservationRepository reservationRepo;

    @Autowired
    private reservationService reservationService;
    public HomeController(UserServiceImp userService){
        this.userService=userService;

    }


    @GetMapping("/" )
    public String Home(Model model){
        Set<cars> carsSet=reservationService.sortedReservation();
        List<cars> Cars=new ArrayList<>();

        int count=0;
        for(cars i:carsSet){
            Cars.add(i);
            count++;
            if(count==3)
                break;
        }
        System.out.println(Cars);
        model.addAttribute("page","home");
        model.addAttribute("cars",Cars);
        return "index";

    }

    @GetMapping("/home")
    public String Index(Model model){
        model.addAttribute("page","home");
        return "index";
    }

    @GetMapping("/search")
    public String Search(@RequestParam(value = "type",required = false) String type,
                         @RequestParam(value = "brand",required = false)String brand,
                         @RequestParam (value="costSearch") String costSearch,
                         Pageable pageable, Model model){
        String[] cost=costSearch.split(" ");
        int costMin=Integer.parseInt(cost[0]);
        int costMax=Integer.parseInt(cost[1]);
        Page<cars> results= carsRepository.findAllByType(type,brand,costMin,costMax,pageable);
        model.addAttribute("cars",results==null? Page.empty():results);
        model.addAttribute("type",null);
        return "search";

    }

    @GetMapping("/search_name")
    public String SearchbyName(@RequestParam(value = "name",required = false) String name,
                         Model model,Pageable pageable){
       Page<cars> listCar=carsRepository.findAllByName(name,pageable);

        model.addAttribute("cars",listCar);
        return "search";

    }

    @GetMapping( "/add")
    public String insertCars(){
        cars c1=new cars("Mercedes-AMG G63","Mercedes-Benz",
                     "5 ch???",3000000,500000,"","Mercedes-Benz_G63_AMG.jpg","2021","S??? t??? ?????ng");

        cars c2=new cars("Lamborghini Aventador LP 700-4","Lamborghini",
                "2 ch???",2500000,500000,"","Lamborghini_Aventador_3.jpg","2019","S??? t??? ?????ng");

        cars c3=new cars("Huyndai Accent 2020","Huyndai",
                "5 ch???",700000,500000,"","Hyndai_Accent.jpg","2021","S??? s??n");
        List<cars> car= Arrays.asList(c1,c2,c3);

        carsRepository.saveAll(car);
        return "add sucess" ;

    }
    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @GetMapping("/signup")
    public String Signup(Model model){
        model.addAttribute("user",new Users());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUserAccount( Users user) {
        if(userService.usersExists(user.getEmail())){
            return "redirect:/signup?errors";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(RoleEnum.User);

        usersRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/car")
    public String allCar(Model model) {
        model.addAttribute("page","car");
        return viewPage(model, 1,"name","asc");
    }

    @GetMapping("/page/{pageNum}")
    public String viewPage(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir){
        Page<cars> page=carsService.listAll(pageNum,sortField,sortDir);
        List<cars> listCars=page.getContent();

        int totalPages=page.getTotalPages();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("listCars",listCars);


        model.addAttribute("page","car");

        return "car";
    }
    @GetMapping("/car_detail/{id}")
    public String carDetail(Model model,@PathVariable(name = "id") long id){
        cars car=carsRepository.findById(id);
        model.addAttribute("car",car);
        return "car-detail";
    }



}
