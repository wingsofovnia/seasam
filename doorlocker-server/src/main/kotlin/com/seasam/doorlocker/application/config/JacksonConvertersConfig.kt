package com.seasam.doorlocker.application.config

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.module.SimpleSerializers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConvertersConfig {

    @Bean
    fun customSerializationModule(deserializers: List<JsonDeserializer<*>>,
                                  serializers: List<JsonSerializer<*>>): Module {
        val simpleSerializers = serializers.run { SimpleSerializers(this) }

        val simpleDeserializers = deserializers
            .map { it.handledType() to it }
            .toMap().run { SimpleDeserializers(this) }

        return SimpleModule().apply {
            setSerializers(simpleSerializers)
            setDeserializers(simpleDeserializers)
        }
    }
}
