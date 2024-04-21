package  com.foodapps.data.network.model.menus

import com.foodapps.data.source.network.model.menus.MenuItemResponse
import com.google.gson.annotations.SerializedName


data class MenuResponse(
    @SerializedName("status")
    val status : Boolean?,
    @SerializedName("code")
    val code : Int?,
    @SerializedName("message")
    val message : String?,
    @SerializedName("data")
    val data : List<MenuItemResponse>?,
)
