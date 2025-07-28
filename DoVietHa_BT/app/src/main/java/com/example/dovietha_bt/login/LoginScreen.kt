package com.example.dovietha_bt.login

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.Destination
import com.example.dovietha_bt.R
import com.example.dovietha_bt.model.userList
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

@Preview(showBackground = true)
@Composable
fun LoginScreen(username:String = "", password:String = "", onClick:() -> Unit = {}, onLogin:()->Unit = {}) {
    var username by remember { mutableStateOf(username) }
    var password by remember { mutableStateOf(password) }
    var isShowPassword by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        DoVietHa_BTTheme {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painterResource(R.drawable.img_logo),
                    "",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
                Text(
                    "Login to your account",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.padding(16.dp))
                InfoInput(
                    leadingIcon = R.drawable.ic_user_outline,
                    hint = "Username",
                    value = username,
                    onValueChange = { username = it })
                PasswordInput(
                    hint = "Password",
                    isShowPassword = isShowPassword,
                    onToggle = { isShowPassword = !isShowPassword },
                    value = password,
                    onValueChange = { password = it }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {}
                    )
                    Text("Remember me", fontWeight = Bold, color = MaterialTheme.colorScheme.onBackground)
                }
                Spacer(Modifier.padding(8.dp))
                Button(
                    onClick = {
                        userList.forEach {
                            if(it.username == username){
                                if(it.password == password){
                                    onLogin()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log in")
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    "Don't have an account? ",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary,
                            offset = Offset(0f, 0f),
                            blurRadius = 14f
                        )
                    )
                )
                Text(
                    "Sign Up",
                    fontWeight = Bold,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary,
                            offset = Offset(0f, 0f),
                            blurRadius = 14f
                        )
                    ),
                    modifier = Modifier.clickable(onClick = onClick)
                )
            }

        }
    }
}
