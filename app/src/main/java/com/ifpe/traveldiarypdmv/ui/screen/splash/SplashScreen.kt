package com.ifpe.traveldiarypdmv.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.theme.GreenLight
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onNavigateToLogin: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        delay(3_000)
        onNavigateToLogin()
    }

    Box(
        modifier = modifier
            .background(GreenLight)
            .fillMaxSize(),
    ) {
        Text(
            text = "Travel Diary",
            style = Typography.headlineLarge.copy(fontSize = 26.sp),
            modifier = Modifier
                .padding(bottom = 25.dp)
                .align(Alignment.Center),

        )
//        Image(
//            modifier = Modifier.align(Alignment.Center),
//            painter = painterResource(id = R.drawable.img_logo_logo_logo_text),
//            contentDescription = "Imagem Logo"
//        )
//        Image(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            painter = painterResource(id = R.drawable.bg_splash_screen),
//            contentDescription = "Imagem Background"
//        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(onNavigateToLogin = {})
}