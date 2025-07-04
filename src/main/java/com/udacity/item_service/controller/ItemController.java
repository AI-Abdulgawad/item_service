package com.udacity.item_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udacity.item_service.model.Dog;

import io.swagger.v3.oas.annotations.parameters.RequestBody;




public interface ItemController {

    @GetMapping("/dogs")
    public Map<Object,Object> getDogs();
    
    
}