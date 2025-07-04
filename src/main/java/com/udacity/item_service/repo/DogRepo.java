package com.udacity.item_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.item_service.model.Dog;

public interface DogRepo extends JpaRepository<Dog,Integer>
{
    
}
