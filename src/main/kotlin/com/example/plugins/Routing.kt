package com.example.plugins

import com.example.dbManager.Connector
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jdbi.v3.core.Jdbi

fun Application.configureRouting(){
    val connector = Connector()
    routing {
        get("/") {
            val str1 = connector.fetchDataFromDatabaseQuery().get(0)
            val str2 = connector.fetchDataFromDatabaseProcedure()
            call.respondText(str1+str2)
        }
    }
}
