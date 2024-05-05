package com.foodapps.data.di

import android.content.SharedPreferences
import com.foodapps.data.datasource.auth.AuthDataSource
import com.foodapps.data.datasource.auth.FireBaseAuthDataSource
import com.foodapps.data.datasource.cart.CartDataSource
import com.foodapps.data.datasource.cart.CartDatabaseDataSource
import com.foodapps.data.datasource.category.CategoryApiDataSource
import com.foodapps.data.datasource.category.CategoryDataSource
import com.foodapps.data.datasource.menu.MenuApiDataSource
import com.foodapps.data.datasource.menu.MenuDataSource
import com.foodapps.data.datasource.pref.PrefDataSource
import com.foodapps.data.datasource.pref.PrefDataSourceImpl
import com.foodapps.data.network.services.FoodAppApiService
import com.foodapps.data.repository.CartRepository
import com.foodapps.data.repository.CartRepositoryImpl
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.CategoryRepositoryImpl
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.MenuRepositoryImpl
import com.foodapps.data.repository.PrefRepository
import com.foodapps.data.repository.PrefRepositoryImpl
import com.foodapps.data.repository.UserRepository
import com.foodapps.data.repository.UserRepositoryImpl
import com.foodapps.data.source.firebase.FirebaseServices
import com.foodapps.data.source.firebase.FirebaseServicesImpl
import com.foodapps.data.source.local.UserPreference
import com.foodapps.data.source.local.UserPreferenceImpl
import com.foodapps.data.source.local.database.AppDatabase
import com.foodapps.data.source.local.database.dao.CartDao
import com.foodapps.presentation.auth.login.LoginViewModel
import com.foodapps.presentation.auth.register.RegisterViewModel
import com.foodapps.presentation.cart.CartViewModel
import com.foodapps.presentation.checkout.CheckoutViewModel
import com.foodapps.presentation.detailmenu.DetailMenuViewModel
import com.foodapps.presentation.home.HomeViewModel
import com.foodapps.presentation.main.MainViewModel
import com.foodapps.presentation.profile.ProfileViewModel
import com.foodapps.presentation.splashscreen.SplashViewModel
import com.foodapps.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val networkModule = module {
        single<FoodAppApiService> { FoodAppApiService.invoke() }
    }

    private val localModule =
        module {
            single<AppDatabase> {
                AppDatabase.createInstance(androidContext())
            }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME
                )
            }
            single<UserPreference> {
                UserPreferenceImpl(get())
            }
        }
    private val firebaseModule =
        module {
            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseServices> {
                FirebaseServicesImpl(get())
            }
        }
    private val dataSourceModule =
        module {
            single<AuthDataSource> {
                FireBaseAuthDataSource(get())
            }
            single<CartDataSource> {
                CartDatabaseDataSource(get())
            }
            single<MenuDataSource> {
                MenuApiDataSource(get())
            }
            single<CategoryDataSource> {
                CategoryApiDataSource(get())
            }
            single<PrefDataSource> {
                PrefDataSourceImpl(get())
            }
        }

    private val repositoryModule =
        module {
            single<CartRepository> {
                CartRepositoryImpl(get())
            }
            single<MenuRepository> {
                MenuRepositoryImpl(get())
            }
            single<CategoryRepository> {
                CategoryRepositoryImpl(get())
            }
            single<PrefRepository> {
                PrefRepositoryImpl(get())
            }
            single<UserRepository> {
                UserRepositoryImpl(get())
            }
        }


    private val viewModelModule =
        module {
            viewModelOf(::MainViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::SplashViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::DetailMenuViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModel { params ->
                DetailMenuViewModel(
                    intent = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            firebaseModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}



