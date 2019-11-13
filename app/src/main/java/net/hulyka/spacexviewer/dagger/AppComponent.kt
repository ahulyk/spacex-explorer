package net.hulyka.spacexviewer.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import net.hulyka.spacexviewer.App
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        MapperModule::class,
        FragmentBuilderModule::class,
        ActivityBuilderModule::class,
        AndroidInjectionModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>
}