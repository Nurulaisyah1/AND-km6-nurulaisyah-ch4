package com.foodapps.data.datasource.category

import com.foodapps.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return listOf(
            Category(
                name = "Rice",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_rice.png")
            ),
            Category(
                name = "Mie",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_noodle.png")
            ),
            Category(
                name = "Drink",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_drink.png")
            ),
            Category(
                name = "Snack",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_snack.png")
            ),
            Category(
                name = "Bread",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_bread.png")
            ),
            Category(
                name = "SeaFood",
                imgUrl = ("https://github.com/Nurulaisyah1/Food_Asset/blob/main/category_img/img_seafood.png")
            ),
        )
    }
}