package com.example.dovietha_bt.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovietha_bt.R
import com.example.dovietha_bt.model.User
import com.example.dovietha_bt.model.userList
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme


@Composable
fun SignUpScreen(onClick: (String, String) -> Unit) {
    DoVietHa_BTTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            var isValid by remember { mutableStateOf(true) }
            var errorUsername by remember { mutableStateOf(false) }
            var errorPassword by remember { mutableStateOf(false) }
            var errorConfirmPass by remember { mutableStateOf(false) }
            var errorEmail by remember { mutableStateOf(false) }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var isShowPassword by remember { mutableStateOf(false) }
            var isShowConfirmPassword by remember { mutableStateOf(false) }
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Image(
                        painterResource(R.drawable.img_logo),
                        "",
                        modifier = Modifier
                            .size(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        "Sign Up",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                Spacer(Modifier.padding(16.dp))
                InfoInput(
                    R.drawable.ic_user_outline,
                    "Username",
                    value = username,
                    onValueChange = {
                        errorUsername = false
                        username = it
                    },
                    checkError = errorUsername
                )
                PasswordInput(
                    hint = "Password",
                    isShowPassword = isShowPassword,
                    onToggle = { isShowPassword = !isShowPassword },
                    value = password,
                    onValueChange = {
                        errorPassword = false
                        password = it
                    },
                    checkError = errorPassword
                )
                PasswordInput(
                    hint = "Confirm password",
                    isShowPassword = isShowConfirmPassword,
                    onToggle = {
                        errorConfirmPass = false
                        isShowConfirmPassword = !isShowConfirmPassword
                    },
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    checkError = errorConfirmPass
                )
                InfoInput(
                    R.drawable.ic_email,
                    "Email",
                    value = email,
                    onValueChange = {
                        errorEmail = false
                        email = it
                    },
                    checkError = errorEmail
                )
            }
            Button(
                onClick = {
                    isValid = true
                    errorUsername =
                        Regex("[^a-z0-9]").containsMatchIn(username.lowercase()) || username.isEmpty()
                    errorPassword =
                        Regex("[^a-zA-Z0-9]").containsMatchIn(password) || password.isEmpty()
                    errorConfirmPass =
                        Regex("[^a-zA-Z0-9]").containsMatchIn(confirmPassword) || confirmPassword != password
                    errorEmail =
                        !Regex("[a-z0-9._-]+@apero\\.com$").matches(email) || email.isEmpty()

                    if (errorUsername) {
                        username = ""
                        isValid = false
                    }
                    if (errorPassword) {
                        password = ""
                        isValid = false
                    }
                    if (errorConfirmPass) {
                        confirmPassword = ""
                        isValid = false
                    }
                    if (errorEmail) {
                        email = ""
                        isValid = false
                    }
                    if (isValid) {
                        Log.d("isValid", "True")
                        userList += User(username, password, email)
                        onClick(username, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text("Sign up", fontSize = 18.sp)
            }

        }
    }
}