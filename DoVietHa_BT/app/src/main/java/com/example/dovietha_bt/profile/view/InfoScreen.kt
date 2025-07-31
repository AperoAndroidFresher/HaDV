package com.example.dovietha_bt.profile.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.dovietha_bt.R
import com.example.dovietha_bt.profile.InfoEvent
import com.example.dovietha_bt.profile.InfoIntent
import com.example.dovietha_bt.profile.InfoScreenViewModel
import com.example.dovietha_bt.profile.UserInformation
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
//Intent: Edit profile
//Event: Check
fun InfoScreen(viewModel: InfoScreenViewModel = viewModel()) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val eventFlow = viewModel.event
    var showDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.processIntent(InfoIntent.UpdateAvatar(uri))
        }
    }

    LaunchedEffect(Unit) {
        eventFlow.collect {event ->
            when(event){
                is InfoEvent.ShowDialog -> {
                    showDialog = true
                    delay(2000)
                    showDialog = false
                }
                InfoEvent.ShowImagePicker -> {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = state.value.currentTheme.color,
        typography = state.value.currentTheme.typo,
        shapes = state.value.currentTheme.shape
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
                        painter = painterResource(if (state.value.isDarkMode) R.drawable.ic_darkmode else R.drawable.ic_lightmode),
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable(onClick = {
                                viewModel.processIntent(InfoIntent.ToggleTheme)
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
                    if (!state.value.isEditing) {
                        Icon(
                            painterResource(R.drawable.ic_edit),
                            "",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .clickable(onClick = { viewModel.processIntent(InfoIntent.Editable) }),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(Modifier.padding(16.dp))
                Box(Modifier.height(168.dp)) {
                    Image(
                        painter =
                            if (state.value.avatarUri == null) painterResource(R.drawable.avatar)
                            else {
                                rememberAsyncImagePainter(
                                    model = ImageRequest
                                        .Builder(context)
                                        .data(state.value.avatarUri)
                                        .size(300, 300)
                                        .scale(Scale.FILL)
                                        .build()
                                )
                            },
                        contentDescription = "",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    if (state.value.isEditing) {
                        Icon(
                            painterResource(R.drawable.ic_camera),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                .padding(5.dp)
                                .clickable(onClick = {
                                    viewModel.processIntent(InfoIntent.TriggerImagePicker)
                                }),
                            tint = Color.White,
                        )
                    }

                }
                Spacer(Modifier.padding(16.dp))
                Row(Modifier.fillMaxWidth()) {

                    InputText(
                        "NAME",
                        "Enter your name...",
                        modifier = Modifier.weight(1f),
                        value = state.value.name,
                        onValueChange = {
                            viewModel.processIntent(InfoIntent.UpdateName(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ),
                        editable = state.value.isEditing,
                        isError = state.value.nameError
                    )

                    Spacer(Modifier.padding(8.dp))

                    InputText(
                        "PHONE NUMBER",
                        "Your phone number...",
                        modifier = Modifier.weight(1f),
                        value = state.value.phone,
                        onValueChange = {
                            viewModel.processIntent(InfoIntent.UpdatePhone(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        editable = state.value.isEditing,
                        isError = state.value.phoneError
                    )
                }

                InputText(
                    "UNIVERSITY NAME",
                    "Your university name...",
                    value = state.value.uni,
                    onValueChange = {
                        viewModel.processIntent(InfoIntent.UpdateUni(it))
                    },
                    editable = state.value.isEditing,
                    isError = state.value.uniError
                )

                InputText(
                    "DESCRIBE YOURSELF",
                    "Enter a description about yourself...",
                    isSingleLine = false,
                    textFieldModifier = Modifier.height(200.dp),
                    value = state.value.desc,
                    onValueChange = { viewModel.processIntent(InfoIntent.UpdateDesc(it)) },
                    editable = state.value.isEditing
                )

                if (state.value.isEditing) {
                    Button(
                        onClick = {
                            viewModel.processIntent(InfoIntent.SubmitInfo)
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
                if(showDialog) {
                    Dialog(
                        onDismissRequest = {
                            showDialog = false
                        }
                    ) {
                        AnimatedVisibility(
                            visible = showDialog,
                            enter = fadeIn(animationSpec = tween(1000)),
                            exit = fadeOut(animationSpec = tween(1000))
                        ) {
                            Column(
                                modifier = Modifier
                                    .size(355.dp, 340.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(20.dp)
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

                }
            }
        }
    }
}

fun validation(
    name: String,
    phone: String,
    university: String,
    desc: String,
    avatarPath: Uri?,
    setNameError: (Boolean) -> Unit,
    setPhoneError: (Boolean) -> Unit,
    setUNameError: (Boolean) -> Unit,
    onSuccess: () -> Unit
) {
    val nameErr = Regex("[^a-zA-Z]").containsMatchIn(name) || name.isEmpty()
    val phoneErr = Regex("[^0-9]").containsMatchIn(phone) || phone.isEmpty()
    val uniErr = Regex("[^a-zA-Z]").containsMatchIn(university) || university.isEmpty()

    setNameError(nameErr)
    setPhoneError(phoneErr)
    setUNameError(uniErr)

    if (!nameErr && !phoneErr && !uniErr) {
        UserInformation.name = name
        UserInformation.phone = phone
        UserInformation.university = university
        UserInformation.desc = desc
        UserInformation.image = avatarPath
        onSuccess()
    }
}