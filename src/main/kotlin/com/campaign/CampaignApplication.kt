package com.campaign

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CampaignApplication

fun main(args: Array<String>) {
    runApplication<CampaignApplication>(*args)
}
