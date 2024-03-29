package com.foodapps.data.datasource.Menu

import com.foodapps.data.model.Menu

class DummyMenuDataSource : MenuDataSource {

    override fun getMenus(): List<Menu> {
        return listOf(
            Menu(
                name = "Nasi Goreng",
                imgUrl ="https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_fried_rice.jpeg?raw=true",
                price = 5.99,
                desc = "Nasi Goreng adalah makanan khas Indonesia yang terdiri dari nasi yang digoreng dalam minyak dengan bumbu dan rempah-rempah khusus, seringkali disajikan dengan telur mata sapi, irisan mentimun, dan kerupuk."
            ),
            Menu(
                name = "Ayam Goreng",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_fried_chiken.jpeg?raw=true",
                price = 7.99,
                desc = "Ayam Goreng adalah hidangan ayam yang digoreng dengan rempah-rempah khusus hingga renyah dan berwarna kecoklatan di luar dan lembut di dalam."
            ),
            Menu(
                name = "Mie Goreng",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_fried_noodles.jpeg?raw=true",
                price = 6.99,
                desc = "Mie Goreng adalah hidangan mie yang digoreng dalam minyak sayur dengan bumbu dan saus, seringkali disajikan dengan potongan telur, daging, dan sayuran."
            ),
            Menu(
                name = "Sate Ayam",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_chiken_satay.jpeg?raw=true",
                price = 8.99,
                desc = "Sate Ayam adalah makanan Indonesia yang terdiri dari potongan daging ayam yang ditusuk dan dipanggang di atas bara api, disajikan dengan saus kacang dan irisan bawang merah."
            ),
            Menu(
                name = "Cappucino",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_cappucino.jpg?raw=true",
                price = 6.49,
                desc = " Cappuccino adalah minuman kopi yang terdiri dari espresso, susu, dan busa susu"
            ),
            Menu(
                name = "Rendang",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_rendang.jpeg?raw=true",
                price = 9.99,
                desc = "Rendang adalah masakan daging asli dari Minangkabau, Sumatra Barat, Indonesia, yang dimasak dalam santan dan rempah-rempah khusus hingga kuahnya habis dan dagingnya berwarna kecoklatan."
            ),
            Menu(
                name = "pizza",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_pizza.jpg?raw=true",
                price = 5.49,
                desc = "Pizza adalah hidangan kuliner yang berasal dari Italia, tetapi telah menjadi makanan internasional yang sangat populer di seluruh dunia. Secara tradisional, pizza terdiri dari adonan roti pipih yang dibentuk bulat dan diminyaki dengan berbagai macam topping, kemudian dipanggang dalam oven."
            ),
            Menu(
                name = "ice tea",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_ice_tea.jpeg?raw=true",
                price = 4.99,
                desc = "Es teh adalah minuman yang terbuat dari teh yang didinginkan dan disajikan dengan es."
            ),
            Menu(
                name = "Salmon sushi",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_salmon-sushi.jpg?raw=true",
                price = 5.99,
                desc = "Salmon sushi adalah hidangan Jepang yang terdiri dari irisan salmon segar yang disajikan di atas sepotong nasi yang dibentuk dengan tangan. Ini adalah salah satu jenis sushi yang paling populer di seluruh dunia."
            ),
            Menu(
                name = "dumplings",
                imgUrl = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/Menu/img_dumplings.jpg?raw=true",
                price = 3.49,
                desc = "Dumpling adalah jenis makanan yang terdiri dari adonan yang dibentuk bulat atau persegi, yang biasanya diisi dengan berbagai macam bahan seperti daging cincang, sayuran, atau campuran keduanya."
            ),
        )
    }
}
