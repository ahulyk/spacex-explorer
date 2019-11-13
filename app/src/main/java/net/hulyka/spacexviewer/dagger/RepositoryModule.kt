package net.hulyka.spacexviewer.dagger

import dagger.Binds
import dagger.Module
import net.hulyka.spacexviewer.repository.FileDownloadRepository
import net.hulyka.spacexviewer.repository.FileDownloadRepositoryImpl
import net.hulyka.spacexviewer.repository.LaunchRepository
import net.hulyka.spacexviewer.repository.LaunchRepositoryImpl
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideLaunchRepository(repository: LaunchRepositoryImpl): LaunchRepository

    @Binds
    @Singleton
    abstract fun provideFileRepository(repository: FileDownloadRepositoryImpl): FileDownloadRepository


}