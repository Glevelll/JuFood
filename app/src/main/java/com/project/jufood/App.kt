package com.project.jufood

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.project.jufood.data.local.RecipesDatabase
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

class App : Application(), DIAware {

    private val databaseModule = DI.Module("databaseModule") {
        bind<RecipesDatabase>() with singleton {
            Room.databaseBuilder(
                instance<Context>(),
                RecipesDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

    override val di = DI.lazy {
        import(androidXModule(this@App))
        import(databaseModule)
    }

    companion object {
        private const val DATABASE_NAME = "recipes_db"
    }
}