package com.webflux.postgres.repositories

import com.webflux.postgres.model.Car
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Repository
class CarRentalRepositoryImpl(val client: DatabaseClient ) : CarRentalRepository {


    override fun findAll(): Flux<Car> {
        return client
                .sql("select * from Car")
                .fetch()
                .all()
                .map { it.mapToCar() }

    }

    override fun getByModel(model: Mono<String>): Mono<Car> {
        TODO("Not yet implemented")
    }

    override fun addModel(monoCar: Mono<Car>): Mono<Car> {
        return monoCar.flatMap {
            Mono.from(client.connectionFactory.create())
                    .flatMap { connection ->
                        Mono.from(connection.beginTransaction())
                                .then(Mono.from(connection.createStatement("insert into Car(id, model) values (null, $1)")
                                        .bind("$1", it.model)
                                        .returnGeneratedValues("id")
                                        .execute()
                                        .toMono().log()
                                        .flatMap { result ->
                                            client.sql("find * where id = $2")
                                                    .bind("$2", result)
                                                    .fetch()
                                                    .one()
                                                    .log()
                                                    .map { it.mapToCar() } })) } } }

    override fun createDb(): Mono<Void> = Mono.from(client.connectionFactory.create())
             .flatMap {
                 it.createBatch()
                         .add("CREATE TABLE Car(id SERIAL PRIMARY KEY, model VARCHAR NOT NULL)")
                         .add("insert into Car values (1, 'FIAT')")
                         .add("insert into Car values (2, 'AUDI')")
                         .execute()
                         .toMono().log().then()
             }






}
fun MutableMap<String,Any>.mapToCar(): Car{
    val id = this.get("id") as Long
    val model = this.get("model") as String
    return Car(id,model)
}
