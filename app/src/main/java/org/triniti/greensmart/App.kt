package org.triniti.greensmart

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.triniti.greensmart.data.db.databases.UserDatabase
import org.triniti.greensmart.data.network.LoginApi
import org.triniti.greensmart.data.network.NetworkConnectionInterceptor
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModelFactory

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { LoginApi(instance()) }
        bind() from singleton { UserDatabase(instance()) }
        bind() from singleton { LoginRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }

    }
}