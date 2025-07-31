package com.example.dovietha_bt.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(changeScreen: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // Hiển thị 3
        changeScreen()
    }
    ContentSplash()
}

@Preview(showBackground = true)
@Composable
fun ContentSplash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 64.dp),

        ) {
        Box(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(Color(0xFF00BCD4))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Apero Music",
                color = Color(0xFF00BCD4),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 50.sp,
                fontWeight = Bold,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
