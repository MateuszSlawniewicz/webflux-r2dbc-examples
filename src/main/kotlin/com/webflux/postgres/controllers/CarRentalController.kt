package com.webflux.postgres.controllers

import com.webflux.postgres.model.Car
import com.webflux.postgres.repositories.CarRentalRepository
import com.webflux.postgres.services.CarRentalService
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct
@Slf4j
@RestController
class CarRentalController(val carRentalService: CarRentalService, val carRentalRepository: CarRentalRepository) {

    fun getAllCars(): Flux<Car>{
        return carRentalService.getAllCars()
    }

    fun addCar(monoCar: Mono<Car>): Mono<Car>{
        return carRentalService.addCar(monoCar)
    }


    @PostConstruct
    fun addToDb(){
    carRentalRepository.createDb().subscribe()


    }

}
