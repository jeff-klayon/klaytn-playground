package com.krustuniverse.klayon.playground

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper = jacksonObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

data class UserCredential(val password: String,
                          val privateKey: String,
                          val walletKey: String,
                          val walletAddress: String) {
    companion object {
        fun load(resourceFile: String) = objectMapper.readValue(this.javaClass.classLoader.getResourceAsStream(resourceFile), UserCredential::class.java)
    }
}