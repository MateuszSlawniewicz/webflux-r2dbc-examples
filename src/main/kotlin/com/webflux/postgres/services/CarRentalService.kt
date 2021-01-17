package com.webflux.postgres.services

import com.webflux.postgres.model.Car
import com.webflux.postgres.repositories.CarRentalRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CarRentalService(val carRentalRepository: CarRentalRepository) {


    fun addCar(monoCar: Mono<Car>): Mono<Car>{
        return carRentalRepository.addModel(monoCar)
    }

    fun getAllCars():Flux<Car>{
        return carRentalRepository.findAll()
    }

}
