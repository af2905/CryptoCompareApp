package ru.job4j.cryptocompareapp.repository

import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

class AppRepository(
    private val communicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {

}

