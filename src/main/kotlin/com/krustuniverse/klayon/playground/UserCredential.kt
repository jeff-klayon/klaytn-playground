package com.krustuniverse.klayon.playground

import com.krustuniverse.klayon.commons.utils.jackson.Jackson

data class UserCredential(val password: String,
                          val privateKey: String,
                          val walletKey: String,
                          val walletAddress: String) {
    companion object {
        fun load(resourceFile: String): UserCredential = Jackson.mapper().readValue(this.javaClass.classLoader.getResourceAsStream(resourceFile), UserCredential::class.java)
    }
}