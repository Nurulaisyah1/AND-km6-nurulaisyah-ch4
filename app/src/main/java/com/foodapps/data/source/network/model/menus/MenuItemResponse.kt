package  com.foodapps.data.source.network.model.menus

import com.google.gson.annotations.SerializedName


data class MenuItemResponse(
    @SerializedName("id")
    val id : String?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("img_url")
    val imgUrl : String?,
    @SerializedName("price")
    val price : Double?,
    @SerializedName("description")
    val description : String?,
)
