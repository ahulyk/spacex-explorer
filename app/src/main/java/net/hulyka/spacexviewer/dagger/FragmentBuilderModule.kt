package net.hulyka.spacexviewer.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.hulyka.spacexviewer.ui.chart.ChartFragment
import net.hulyka.spacexviewer.ui.chart.ChartModule
import net.hulyka.spacexviewer.ui.details.DetailsFragment
import net.hulyka.spacexviewer.ui.details.DetailsModule
import net.hulyka.spacexviewer.ui.home.HomeFragment
import net.hulyka.spacexviewer.ui.home.HomeModule

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ChartModule::class])
    abstract fun bindChartFragment(): ChartFragment

    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun bindDetailsFragment(): DetailsFragment

}


