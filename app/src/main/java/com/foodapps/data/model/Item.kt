// com.foodapps.model.Item.kt

package com.foodapps.model

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Item(
    val id: String,
    val name: String,
    val price: Double,
) {
    fun addToCart(context: Context) {
        // Tambahkan logika untuk menambahkan item ke keranjang di sini
        val sharedPreferences = context.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Mendapatkan daftar item yang sudah ada di keranjang
        val json = sharedPreferences.getString("cart_items", null)
        val itemList: MutableList<Item> =
            if (json != null) {
                val type = object : TypeToken<List<Item>>() {}.type
                Gson().fromJson(json, type)
            } else {
                ArrayList()
            }

        // Menambahkan item baru ke daftar
        itemList.add(this)

        // Menyimpan daftar item yang diperbarui ke SharedPreferences
        val updatedJson = Gson().toJson(itemList)
        editor.putString("cart_items", updatedJson)
        editor.apply()

        // Tampilkan pesan bahwa item telah ditambahkan ke keranjang
        Toast.makeText(context, "add to cart success", Toast.LENGTH_SHORT).show()
    }
}
