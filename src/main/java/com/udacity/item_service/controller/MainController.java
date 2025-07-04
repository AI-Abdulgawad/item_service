package com.udacity.item_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udacity.item_service.model.Dog;
import com.udacity.item_service.repo.DogRepo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Controller
public class MainController {

    @Autowired
    DogRepo repo;

    @GetMapping("/getAllDogs")
    @ResponseBody
    public List<Dog> getAllDogs() {

        return repo.findAll();
    }
    
}
