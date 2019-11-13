package net.hulyka.spacexviewer.ui.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.hulyka.spacexviewer.dagger.annotation.ViewModelKey

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel

}