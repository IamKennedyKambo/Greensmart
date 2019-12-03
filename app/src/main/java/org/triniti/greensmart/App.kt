package org.triniti.greensmart

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.NetworkConnectionInterceptor
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.data.repositories.*
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.ui.home.bins.BinsViewModelFactory
import org.triniti.greensmart.ui.home.cart.CartViewModelFactory
import org.triniti.greensmart.ui.home.shop.mall.MallViewModelFactory
import org.triniti.greensmart.ui.home.shop.single.ProductsViewModelFactory
import org.triniti.greensmart.utilities.DataViewModel

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { GreenApi(instance()) }
        bind() from singleton { DataViewModel() }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { ProductsRepository(instance()) }
        bind() from singleton { CartRepository(instance()) }
        bind() from singleton { AuthRepository(instance(), instance()) }
        bind() from singleton { BinsRepository(instance(), instance(), instance()) }
        bind() from singleton { MallRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance()) }
        bind() from provider { AboutViewModelFactory(instance()) }
        bind() from provider { BinsViewModelFactory(instance()) }
        bind() from provider { MallViewModelFactory(instance()) }
        bind() from provider { CartViewModelFactory(instance()) }
        bind() from provider { ProductsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}