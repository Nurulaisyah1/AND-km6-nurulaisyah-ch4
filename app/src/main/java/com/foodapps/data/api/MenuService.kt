package com.foodapps.data.api

import com.foodapps.data.model.Menu
import retrofit2.Call
import retrofit2.http.GET

interface MenuService {
    @GET("menus")
    fun getMenuList(): Call<List<Menu>>
}