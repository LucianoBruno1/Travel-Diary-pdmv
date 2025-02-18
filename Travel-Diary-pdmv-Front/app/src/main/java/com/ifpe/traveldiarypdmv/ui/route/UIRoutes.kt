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

@Serializable
data object Profile {
    const val route = "profile"
}

@Serializable
data object Details {
    const val route = "details"
}

@Serializable
data object RecoverPassword {
    const val route = "recoverpassword"
}

@Serializable
data object ResetPassword {
    const val route = "resetpassword"
}

@Serializable
data object Camera {
    const val route = "camera"
}

@Serializable
data object CreateDiary {
    const val route = "creatediary"
}