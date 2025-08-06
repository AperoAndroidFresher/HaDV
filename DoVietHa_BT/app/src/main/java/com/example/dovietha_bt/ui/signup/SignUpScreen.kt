package com.example.dovietha_bt.ui.signup

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.ui.components.InfoInput
import com.example.dovietha_bt.ui.components.PasswordInput
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme


@Composable
fun SignUpScreen(
    onClick: (String, String) -> Unit,
    viewModel: SignUpScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                SignUpEvent.ShowNotify -> {
                    Toast.makeText(context, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    DoVietHa_BTTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

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
                    value = state.value.username,
                    onValueChange = {
                        viewModel.processIntent(SignUpIntent.EditUsername(it))
                    },
                    checkError = state.value.errUsername
                )
                PasswordInput(
                    hint = "Password",
                    isShowPassword = state.value.isShowPassword,
                    onToggle = { viewModel.processIntent(SignUpIntent.IsShowPassword) },
                    value = state.value.password,
                    onValueChange = {
                        viewModel.processIntent(SignUpIntent.EditPassword(it))
                    },
                    checkError = state.value.errPassword
                )
                PasswordInput(
                    hint = "Confirm password",
                    isShowPassword = state.value.isShowConfirmPass,
                    onToggle = {
                        viewModel.processIntent(SignUpIntent.IsShowConfirmPassword)
                    },
                    value = state.value.confirmPassword,
                    onValueChange = { viewModel.processIntent(SignUpIntent.EditConfirmPass(it)) },
                    checkError = state.value.errConfirmPass
                )
                InfoInput(
                    R.drawable.ic_email,
                    "Email",
                    value = state.value.email,
                    onValueChange = {
                        viewModel.processIntent(SignUpIntent.EditEmail(it))
                    },
                    checkError = state.value.errEmail
                )
            }
            Button(
                onClick = {
                    viewModel.processIntent(SignUpIntent.IsValid)
                    if (state.value.isValid) {
                        Log.d("LOG", "$state.value.isValid")
                        onClick(state.value.username, state.value.password)
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