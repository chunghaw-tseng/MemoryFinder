package com.example.memoryfinder

import android.app.Application
import com.example.memoryfinder.data.PexelDao
import com.example.memoryfinder.data.PexelDaoImpl
import com.example.memoryfinder.data.PhotoDataCache
import com.example.memoryfinder.data.network.*
import com.example.memoryfinder.data.provider.SearchProvider
import com.example.memoryfinder.data.provider.SearchProviderImpl
import com.example.memoryfinder.data.repo.PexelsRepository
import com.example.memoryfinder.data.repo.PexelsRepositoryImpl
import com.example.memoryfinder.modelviews.MemoryViewerModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule


class MemoryApplication : Application(), DIAware{
    override val di by DI.lazy {
        import(androidXModule(this@MemoryApplication))

        // Main bindings for Dependency Injection
        bind<PexelDao>() with singleton { PexelDaoImpl() }
        bind<Connectivity>() with singleton { ConnectivityImpl(instance()) }
        bind() from singleton { PexelsApiService(instance()) }
        bind<PexelNetworkDS>() with singleton { PexelNetworkDSImpl(instance()) }
        bind<PexelsRepository>() with singleton { PexelsRepositoryImpl(instance(), instance()) }
//        bind<SearchProvider>() with singleton { SearchProviderImpl() }
        bind() from provider { MemoryViewerModelFactory(instance()) }
    }

}