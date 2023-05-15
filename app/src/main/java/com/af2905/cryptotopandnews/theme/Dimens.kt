package com.af2905.cryptotopandnews.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    //Spaces
    val spaceTiny: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceNormal: Dp = 16.dp,
    val spaceBig: Dp = 32.dp,
    val spaceHuge: Dp = 64.dp,

    //Radius
    val radiusTiny: Dp = 4.dp,
    val radiusSmall: Dp = 8.dp,
    val radiusNormal: Dp = 12.dp,
    val radiusBig: Dp = 16.dp,

    //Images
    val imageSmall: Dp = 24.dp,
    val imageNormal: Dp = 42.dp,
    val imageLarge: Dp = 64.dp,

    //Stroke
    val strokeTiny: Dp = 0.5.dp,
    val strokeSmall: Dp = 1.dp,

    //Elevation
    val noElevation: Dp = 0.dp
)

internal val dimens: Dimens = Dimens()