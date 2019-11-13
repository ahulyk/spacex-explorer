package net.hulyka.spacexviewer.dagger

import dagger.Binds
import dagger.Module
import net.hulyka.spacexviewer.mapper.LaunchInfoMapper
import net.hulyka.spacexviewer.mapper.LaunchInfoMapperImpl
import javax.inject.Singleton

@Module
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun provideMapper(mapper: LaunchInfoMapperImpl): LaunchInfoMapper

}