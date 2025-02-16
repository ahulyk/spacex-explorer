package net.hulyka.spacexviewer.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import net.hulyka.spacexviewer.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}