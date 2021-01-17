package com.webflux.postgres.configurations

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class DatabaseConfiguration: AbstractR2dbcConfiguration() {
  @Bean
  override fun connectionFactory(): ConnectionFactory {
      return PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
              .host("localhost")
              .username("postgres")
              .password("postgres")
              .port(5433)
              .database("reactive")
              .build())
  }


//    @Bean
//    fun databaseClient(connectionFactory: ConnectionFactory): DatabaseClient {
//        return DatabaseClient.builder()
//                .connectionFactory(connectionFactory)
//                .namedParameters(true)
//                .build()
//    }


}
