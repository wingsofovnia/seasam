package com.seasam.doorlocker.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConvertersConfig {

    @Bean
    @Primary
    fun customConversions(converters: List<Converter<*, *>>) = MongoCustomConversions(converters)
}
