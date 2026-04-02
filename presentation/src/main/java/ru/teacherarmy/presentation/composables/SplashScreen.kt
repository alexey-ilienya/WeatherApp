package ru.teacherarmy.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.teacherarmy.presentation.R

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.kaleidoscope))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        val animationStarted = remember { mutableStateOf(false) }
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
            modifier = Modifier.align(Alignment.Center),
        )
        LaunchedEffect(logoAnimationState.isAtEnd) {
            if (logoAnimationState.isAtEnd && animationStarted.value) {
                onAnimationEnd.invoke()
            } else {
                animationStarted.value = true
            }
        }
    }
}
