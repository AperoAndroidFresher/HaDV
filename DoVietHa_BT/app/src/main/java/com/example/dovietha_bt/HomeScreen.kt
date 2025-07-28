package com.example.dovietha_bt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun HomeScreen(goProfile: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth()) {
        Text(text = "Home", fontSize = 40.sp, modifier = Modifier)
        Spacer(Modifier.weight(1f))
        Icon(painterResource(R.drawable.ic_person), "", Modifier.size(40.dp).clickable(onClick = goProfile))
    }
}