package net.hulyka.spacexviewer.dagger

import android.content.Context
import dagger.Binds
import dagger.Module
import net.hulyka.spacexviewer.App
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(context: App): Context

}