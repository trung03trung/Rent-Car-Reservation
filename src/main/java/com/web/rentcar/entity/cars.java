package com.web.rentcar.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "cars")
public class cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String type;


    @Column
    private int cost;



    @Column
    private int lateCheckOutFee;

    @Column
    private String description;

    @Column
    private String image;

    @Column
    private String mileage;

    @Column
    private String transmission;


    public cars(){

    }
    public cars(String name, String brand, String type, int cost,int lateCheckOutFee, String description, String image,String mileage,String transmission) {
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.cost = cost;
        this.lateCheckOutFee=lateCheckOutFee;
        this.description = description;
        this.image = image;
        this.mileage=mileage;
        this.transmission=transmission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String setCostString() {
        String costString=Integer.toString(this.cost);
        String tmp="";
        int index=1;
        for(int i=costString.length()-1;i>=0;i-- ) {
            tmp +=costString.charAt(i);
            if (index % 3 == 0 && i != 0) {
                tmp += ",";
            }
            index++;
        }
       return  costString= String.valueOf(new StringBuffer(tmp).reverse());
    }
    public String setCostString(long cost) {
        String costString=Long.toString(cost);
        String tmp="";
        int index=1;
        for(int i=costString.length()-1;i>=0;i-- ) {
            tmp +=costString.charAt(i);
            if (index % 3 == 0 && i != 0) {
                tmp += ",";
            }
            index++;
        }
        return  costString= String.valueOf(new StringBuffer(tmp).reverse());
    }

    public int getLateCheckOutFee() {
        return lateCheckOutFee;
    }

    public void setLateCheckOutFee(int lateCheckOutFee) {
        this.lateCheckOutFee = lateCheckOutFee;
    }




}
