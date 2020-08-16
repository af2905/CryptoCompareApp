package ru.job4j.cryptocompareapp.di.component

import dagger.Component
import ru.job4j.cryptocompareapp.di.module.ApiModule
import ru.job4j.cryptocompareapp.di.scope.ApiScope
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

@ApiScope
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val serverCommunicator: ServerCommunicator
}
