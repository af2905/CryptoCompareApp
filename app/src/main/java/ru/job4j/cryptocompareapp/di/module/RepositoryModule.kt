package ru.job4j.cryptocompareapp.di.module

import dagger.Module
import dagger.Provides
import ru.job4j.cryptocompareapp.di.scope.RepositoryScope
import ru.job4j.cryptocompareapp.repository.AppRepository
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

@Module
class RepositoryModule {
    @Provides
    @RepositoryScope
    fun providesRepository(communicator: ServerCommunicator, appDatabase: AppDatabase):AppRepository{
        return AppRepository(communicator, appDatabase)
    }
}