package com.easychicken.diabroapp.service

import org.springframework.stereotype.Repository
import com.intersystems.jdbc.IRIS
import com.intersystems.jdbc.IRISConnection
import com.intersystems.jdbc.IRISDataSource
@Repository
class IRISservice {


    fun getConnection() {
        val url = "jdbc:IRIS://localhost:1972/USER"
        val ds = IRISDataSource()
        ds.url = url
        ds.user = "SuperUser"
        ds.password = "a"

        val con = ds.connection
        println("connected")
    }

}