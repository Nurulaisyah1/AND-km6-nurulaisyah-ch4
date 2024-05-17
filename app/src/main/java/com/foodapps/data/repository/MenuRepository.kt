package com.foodapps.data.repository

import com.foodapps.data.datasource.menu.MenuDataSource
import com.foodapps.data.mapper.toMenus
import com.foodapps.data.model.Cart
import com.foodapps.data.model.Menu
import com.foodapps.data.network.model.checkout.CheckoutItemPayload
import com.foodapps.data.network.model.checkout.CheckoutRequestPayload
import com.foodapps.utils.ResultWrapper
import com.foodapps.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categorySlug: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(products: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(
    private val dataSource: MenuDataSource,
) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenus(categorySlug).data.toMenus()
        }
    }

    override fun createOrder(products: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    orders =
                        products.map {
                            CheckoutItemPayload(
                                notes = it.itemNotes,
                                productId = it.menuId.orEmpty(),
                                quantity = it.itemQuantity,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
