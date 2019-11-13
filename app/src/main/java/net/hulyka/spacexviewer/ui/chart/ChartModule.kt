package net.hulyka.spacexviewer.ui.chart

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.hulyka.spacexviewer.dagger.annotation.ViewModelKey

@Module
abstract class ChartModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    internal abstract fun bindViewModel(viewModel: ChartViewModel): ViewModel

}