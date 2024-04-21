package com.foodapps.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.UUID



@Parcelize
data class Menu(
    @SerializedName("name") var name: String,
    @SerializedName("imgUrl") var imgUrl: String = "",
    @SerializedName("price") var price: Double,
    @SerializedName("description") var description: String,
    var id: String = UUID.randomUUID().toString()
) : Parcelable, Serializable {

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
    }
}