package com.ifpe.traveldiarypdmv.ui.route

import kotlinx.serialization.Serializable

@Serializable
data object Splash {
    const val route = "splash"
}

@Serializable
data object Login {
    const val route = "login"
}

@Serializable
data object Home {
    const val route = "home"
}

@Serializable
data object Register {
    const val route = "register"
}