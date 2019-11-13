package net.hulyka.spacexviewer.ui.details

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.hulyka.spacexviewer.dagger.annotation.ViewModelKey

@Module
abstract class DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindViewModel(viewModel: DetailsViewModel): ViewModel

}