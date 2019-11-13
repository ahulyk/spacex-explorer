package net.hulyka.spacexviewer.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.hulyka.spacexviewer.ui.MainActivity


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

}

