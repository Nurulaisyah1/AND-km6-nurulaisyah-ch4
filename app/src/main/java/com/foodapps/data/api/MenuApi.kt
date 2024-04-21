package com.foodapps.data.api

import com.foodapps.data.model.Menu
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface MenuApi {
    @GET("menus/{categorySlug}")
    suspend fun getMenusByCategory(@Path("categorySlug") categorySlug: String): List<Menu>

    @GET("menus")
    suspend fun getAllMenus(): List<Menu>

    @POST("addmenu")
    suspend fun addMenu(@Body menu: Menu)

    @POST("updatemenu")
    suspend fun updateMenu(@Body menu: Menu)

    @POST("deletemenu")
    suspend fun deleteMenu(@Body menu: Menu)
}
