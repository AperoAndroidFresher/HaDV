package com.example.dovietha_bt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoVietHa_BTTheme {
                InfoScreen()
            }
        }
    }
}

@Composable
fun InputText(
    title: String = "Title",
    desc: String = "Desc...",
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    isSingleLine: Boolean = true,

    ) {
    Column(modifier.fillMaxWidth()) {
        Text(title, fontSize = 14.sp, color = Color.DarkGray)
        Spacer(Modifier.padding(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(desc, color = Color.Blue, maxLines = 1, fontSize = 14.sp) },
            modifier = textFieldModifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            singleLine = isSingleLine
        )
        Spacer(Modifier.padding(7.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreen() {
    var name by remember { mutableStateOf("") }
    var phoneNum by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var dialogState by remember { mutableStateOf(false) }
    var editState by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(7.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                "MY INFORMATION",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
            if (!editState) {
                Image(
                    painterResource(R.drawable.ic_edit),
                    "",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clickable(onClick = { editState = true })
                )
            }
        }
        Spacer(Modifier.padding(16.dp))
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.padding(16.dp))
        Row(Modifier.fillMaxWidth()) {
            InputText(
                "NAME",
                "Enter your name...",
                modifier = Modifier.weight(1f),
                value = name,
                onValueChange = { name = it })
            Spacer(Modifier.padding(8.dp))
            InputText(
                "PHONE NUMBER",
                "Your phone number...",
                modifier = Modifier.weight(1f),
                value = phoneNum,
                onValueChange = { phoneNum = it })
        }
        InputText(
            "UNIVERSITY NAME",
            "Your university name...",
            value = university,
            onValueChange = { university = it })
        InputText(
            "DESCRIBE YOURSELF",
            "Enter a description about yourself...",
            isSingleLine = false,
            textFieldModifier = Modifier.height(200.dp),
            value = desc,
            onValueChange = { desc = it }
        )
        if (editState) {
            Button(
                onClick = { dialogState = true },
                Modifier.size(172.dp, 64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 16.sp,
                )
            }
        }
        if (dialogState) {
            Dialog(
                onDismissRequest = {
                    dialogState = false
                    editState = false
                }
            ) {
                Column(
                    modifier = Modifier
                        .size(355.dp, 340.dp)
                        .background(Color.White, RoundedCornerShape(20.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.padding(20.dp))
                    Image(
                        painterResource(R.drawable.img_check),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(Modifier.padding(8.dp))
                    Text(
                        "Success!",
                        fontWeight = Bold,
                        fontSize = 32.sp,
                        color = Color(0xFF2E7D32),
                        style = TextStyle(letterSpacing = 2.sp)
                    )
                    Spacer(Modifier.padding(9.dp))
                    Text(
                        "Your information has been updated!",
                        modifier = Modifier.padding(horizontal = 50.dp),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(letterSpacing = 1.sp)
                    )
                }
            }
        }
    }
}