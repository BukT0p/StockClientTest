package com.mechanitis.demo.stockclient

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfiguration {
    @Bean
    @Profile("sse")
    fun webClientStockClient(webClient: WebClient): IStockClient = WebClientStockClient(webClient)

    @Bean
    @ConditionalOnMissingBean
    fun webClient(): WebClient = WebClient.create()

    @Bean
    @Profile("rSocket")
    fun rSocketClientStockClient(rSocketRequester: RSocketRequester): IStockClient = RSocketClientStockClient(rSocketRequester)

    @Bean
    @ConditionalOnMissingBean
    fun rSocketRequester(builder: RSocketRequester.Builder): RSocketRequester =
            builder.connectTcp("localhost", 7000).block() ?: throw RuntimeException("Not connected")

}