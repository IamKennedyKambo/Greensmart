package org.triniti.greensmart

import android.app.Application
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
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.data.repositories.BinsRepository
import org.triniti.greensmart.ui.auth.viewmodels.AuthViewModelFactory
import org.triniti.greensmart.ui.home.main.viewmodels.BinsViewModelFactory

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { GreenApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { AuthRepository(instance(), instance()) }
        bind() from singleton { BinsRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { BinsViewModelFactory(instance()) }
    }
}