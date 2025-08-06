package com.example.dovietha_bt.ui.main.library

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R

@Preview(showBackground = true)
@Composable
fun DisconnectRemote(modifier: Modifier = Modifier,onTryAgain:()-> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(62.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(R.drawable.ic_remote_disconnect),
            contentDescription = "")

        Spacer(Modifier.padding(8.dp))

        Text(text = "No internet connection, please check your connection again",
            fontSize = 18.sp,
            textAlign = TextAlign.Center)

        Spacer(Modifier.padding(8.dp))

        Button(onClick = onTryAgain) {
            Text("Try again")
        }
    }
}