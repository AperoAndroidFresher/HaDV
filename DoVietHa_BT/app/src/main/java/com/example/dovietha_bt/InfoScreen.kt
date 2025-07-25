package com.example.dovietha_bt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dovietha_bt.ui.theme.darkTheme
import com.example.dovietha_bt.ui.theme.lightTheme
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun InfoScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNum by rememberSaveable { mutableStateOf("") }
    var university by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var dialogState by rememberSaveable { mutableStateOf(false) }
    var editState by rememberSaveable { mutableStateOf(false) }
    var isNameError by rememberSaveable { mutableStateOf(false) }
    var isPhoneError by rememberSaveable { mutableStateOf(false) }
    var isUNameError by rememberSaveable { mutableStateOf(false) }
    var isDarkMode by rememberSaveable { mutableStateOf(false) }
    var currentTheme by remember { mutableStateOf(lightTheme) }
    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typo,
        shapes = currentTheme.shape
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.padding(7.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(if (isDarkMode) R.drawable.ic_darkmode else R.drawable.ic_lightmode),
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable(onClick = {
                                isDarkMode = !isDarkMode
                                currentTheme = if (isDarkMode) {
                                    darkTheme
                                } else lightTheme
                            }),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "MY INFORMATION",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    if (!editState) {
                        Icon(
                            painterResource(R.drawable.ic_edit),
                            "",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .clickable(onClick = { editState = true }),
                            tint = MaterialTheme.colorScheme.primary
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
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.padding(16.dp))
                Row(Modifier.fillMaxWidth()) {

                    InputText(
                        "NAME",
                        "Enter your name...",
                        modifier = Modifier.weight(1f),
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        editable = editState,
                        isError = isNameError
                    )

                    Spacer(Modifier.padding(8.dp))

                    InputText(
                        "PHONE NUMBER",
                        "Your phone number...",
                        modifier = Modifier.weight(1f),
                        value = phoneNum,
                        onValueChange = {
                            phoneNum = it

                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        editable = editState,
                        isError = isPhoneError
                    )
                }

                InputText(
                    "UNIVERSITY NAME",
                    "Your university name...",
                    value = university,
                    onValueChange = {
                        university = it
                    },
                    editable = editState,
                    isError = isUNameError
                )

                InputText(
                    "DESCRIBE YOURSELF",
                    "Enter a description about yourself...",
                    isSingleLine = false,
                    textFieldModifier = Modifier.height(200.dp),
                    value = desc,
                    onValueChange = { desc = it },
                    editable = editState
                )

                if (editState) {
                    Button(
                        onClick = {
                            isNameError = Regex("[^a-zA-Z]").containsMatchIn(name) || name.isEmpty()
                            isPhoneError =
                                Regex("[^0-9]").containsMatchIn(phoneNum) || phoneNum.isEmpty()
                            isUNameError =
                                Regex("[^a-zA-Z]").containsMatchIn(university) || university.isEmpty()
                            if (!isNameError && !isPhoneError && !isUNameError) {
                                dialogState = true
                            }
                        },
                        Modifier.size(172.dp, 64.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors()
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
                        AnimatedVisibility(
                            visible = dialogState,
                            enter = fadeIn(animationSpec = tween(1000)),
                            exit = fadeOut(animationSpec = tween(1000))
                        ) {
                            Column(
                                modifier = Modifier
                                    .size(355.dp, 340.dp)
                                    .background(
                                        Color.White,
                                        androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                                    ),
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
                                    fontWeight = FontWeight.Bold,
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
                    LaunchedEffect(Unit) {
                        delay(2000)
                        dialogState = false
                        editState = false
                    }
                }
            }
        }
    }
}