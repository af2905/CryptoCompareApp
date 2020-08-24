package com.af2905.cryptotopandnews.di.component

import com.af2905.cryptotopandnews.di.module.ApiModule
import com.af2905.cryptotopandnews.di.scope.ApiScope
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import dagger.Component

@ApiScope
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val serverCommunicator: ServerCommunicator
}
