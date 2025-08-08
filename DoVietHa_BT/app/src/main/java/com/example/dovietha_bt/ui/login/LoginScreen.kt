package com.example.dovietha_bt.ui.login

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovietha_bt.R
import com.example.dovietha_bt.common.UserPreferences
import com.example.dovietha_bt.ui.components.InfoInput
import com.example.dovietha_bt.ui.components.PasswordInput
import com.example.dovietha_bt.ui.theme.DoVietHa_BTTheme

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    username: String = "",
    password: String = "",
    onClick: () -> Unit = {},
    onLogin: () -> Unit = {},
    viewModel: LoginScreenViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.processIntent(LoginIntent.InitData(username, password))
        event.collect { event ->
            when (event) {
                LoginEvent.ShowNotify -> {
                    Toast.makeText(context, "Invalid Infomation", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
                    value = state.value.username,
                    onValueChange = { viewModel.processIntent(LoginIntent.EditUsername(it)) })
                PasswordInput(
                    hint = "Password",
                    isShowPassword = state.value.isShowPassword,
                    onToggle = { viewModel.processIntent(LoginIntent.IsShowPassword) },
                    value = state.value.password,
                    onValueChange = { viewModel.processIntent(LoginIntent.EditPassword(it)) }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {}
                    )
                    Text(
                        "Remember me",
                        fontWeight = Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(Modifier.padding(8.dp))
                Button(
                    onClick = {
                        viewModel.processIntent(LoginIntent.IsValid)
                        if (state.value.isValid) onLogin()
                        Log.d("TAG","${UserPreferences.getUsername()}")
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
