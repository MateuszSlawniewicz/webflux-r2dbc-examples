package com.webflux.postgres.repositories

import com.webflux.postgres.model.Car
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CarRentalRepository {
    fun findAll(): Flux<Car>
    fun getByModel(model: Mono<String>): Mono<Car>
    fun addModel(monoCar: Mono<Car>): Mono<Car>
    fun createDb():Mono<Void>
}
