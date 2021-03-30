package com.example.memoryfinder

import android.app.Application
import com.example.memoryfinder.data.network.*
import com.example.memoryfinder.data.repo.PexelsRepo
import com.example.memoryfinder.data.repo.PexelsRepoImpl
import com.example.memoryfinder.modelviews.MemoryViewerModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule


class MemoryApplication : Application(), DIAware{
    override val di by DI.lazy {
        import(androidXModule(this@MemoryApplication))

        // Main bindings for Dependency Injection
        bind<Connectivity>() with singleton { ConnectivityImpl(instance()) }
        bind() from singleton { PexelsApiService(instance()) }
        bind<PexelsRepo>() with singleton { PexelsRepoImpl(instance()) }
        bind() from provider { MemoryViewerModelFactory(instance()) }
    }

}