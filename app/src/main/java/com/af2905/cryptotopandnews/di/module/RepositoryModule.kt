package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.di.scope.RepositoryScope
import com.af2905.cryptotopandnews.repository.AppRepository
import com.af2905.cryptotopandnews.repository.database.AppDatabase
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    @RepositoryScope
    fun providesRepository(communicator: ServerCommunicator, appDatabase: AppDatabase): AppRepository {
        return AppRepository(communicator, appDatabase)
    }
}
