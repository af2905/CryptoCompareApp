package ru.job4j.cryptocompareapp.repository.database.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class CoinPriceInfo(
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromSymbol: String? = null,

    @SerializedName("TOSYMBOL")
    @Expose
    val toSymbol: String? = null,

    @SerializedName("MARKET")
    @Expose
    val market: String? = null,

    @SerializedName("PRICE")
    @Expose
    val price: String? = null,

    @SerializedName("LASTUPDATE")
    @Expose
    val lastUpdate: String? = null,

    @SerializedName("LASTVOLUME")
    @Expose
    val lastVolume: String? = null,

    @SerializedName("LASTVOLUMETO")
    @Expose
    val lastVolumeTo: String? = null,

    @SerializedName("LASTTRADEID")
    @Expose
    val lastTradeId: String? = null,

    @SerializedName("VOLUMEDAY")
    @Expose
    val volumeDay: String? = null,

    @SerializedName("VOLUMEDAYTO")
    @Expose
    val volumeDayTo: String? = null,

    @SerializedName("VOLUME24HOUR")
    @Expose
    val volume24Hour: String? = null,

    @SerializedName("VOLUME24HOURTO")
    @Expose
    val volume24HourTo: String? = null,

    @SerializedName("OPENDAY")
    @Expose
    val openDay: String? = null,

    @SerializedName("HIGHDAY")
    @Expose
    val highDay: String? = null,

    @SerializedName("LOWDAY")
    @Expose
    val lowDay: String? = null,

    @SerializedName("OPEN24HOUR")
    @Expose
    val open24Hour: String? = null,

    @SerializedName("HIGH24HOUR")
    @Expose
    val high24Hour: String? = null,

    @SerializedName("LOW24HOUR")
    @Expose
    val low24Hour: String? = null,

    @SerializedName("LASTMARKET")
    @Expose
    val lastMarket: String? = null,

    @SerializedName("VOLUMEHOUR")
    @Expose
    val volumeHour: String? = null,

    @SerializedName("VOLUMEHOURTO")
    @Expose
    val volumeHourTo: String? = null,

    @SerializedName("OPENHOUR")
    @Expose
    val openHour: String? = null,

    @SerializedName("HIGHHOUR")
    @Expose
    val highHour: String? = null,

    @SerializedName("LOWHOUR")
    @Expose
    val lowHour: String? = null,

    @SerializedName("TOPTIERVOLUME24HOUR")
    @Expose
    val topTierVolume24Hour: String? = null,

    @SerializedName("TOPTIERVOLUME24HOURTO")
    @Expose
    val topTierVolume24HourTo: String? = null,

    @SerializedName("CHANGE24HOUR")
    @Expose
    val change24Hour: String? = null,

    @SerializedName("CHANGEPCT24HOUR")
    @Expose
    val changeRct24Hour: String? = null,

    @SerializedName("CHANGEDAY")
    @Expose
    val changeDay: String? = null,

    @SerializedName("CHANGEPCTDAY")
    @Expose
    val changePctDay: String? = null,

    @SerializedName("CHANGEHOUR")
    @Expose
    val changeHour: String? = null,

    @SerializedName("CHANGEPCTHOUR")
    @Expose
    val changePctHour: String? = null,

    @SerializedName("CONVERSIONTYPE")
    @Expose
    val conversionType: String? = null,

    @SerializedName("CONVERSIONSYMBOL")
    @Expose
    val conversionSymbol: String? = null,

    @SerializedName("SUPPLY")
    @Expose
    val supply: String? = null,

    @SerializedName("MKTCAP")
    @Expose
    val mktCap: String? = null,

    @SerializedName("TOTALVOLUME24H")
    @Expose
    val totalVolume24H: String? = null,

    @SerializedName("TOTALVOLUME24HTO")
    @Expose
    val totalVolume24HTo: String? = null,

    @SerializedName("TOTALTOPTIERVOLUME24H")
    @Expose
    val totalTopTierVolume24H: String? = null,

    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    @Expose
    val totalTopTierVolume24HTo: String? = null,

    @SerializedName("IMAGEURL")
    @Expose
    val imageUrl: String? = null
)