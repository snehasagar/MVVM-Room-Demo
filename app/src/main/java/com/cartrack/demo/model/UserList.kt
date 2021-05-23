package com.cartrack.demo.model

    data class UserList(
            val id : Int?,
            val username : String?,
            val name : String?,
            val email : String?,
            val address : addressData?
    )
    data class addressData(
            val city : String?,
            val street : String?,
            val geo : geoData?
    )
    data class geoData(
            val lat : String?,
            val lng : String?
    )





