package ru.job4j.cryptocompareapp.repository.server

import io.reactivex.Flowable
import ru.job4j.cryptocompareapp.repository.database.entity.Datum

class ServerCommunicator(private val apiService: ApiService) {
    fun getCoinPriceInfo(): Flowable<List<Datum>> {
        return apiService.getTopCoinsInfo()
                    .map {
                if (!it.data.isNullOrEmpty())
                    for (datum in it.data) {
                        datum.id = datum.coinBasicInfo.coinBasicId.toInt()
                    }
                return@map it.data
            }
    }
}
