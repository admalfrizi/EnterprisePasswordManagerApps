package org.apps.simpenpass.presentation.ui.group_pass.settings_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.presentation.components.profileComponents.SettingsListHolder
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.profileNameInitials
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.group_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupSettingsScreen(
    groupSettingsViewModel: GroupSettingsViewModel = koinViewModel(),
    navToEditRole : (String) -> Unit,
    navToBack : () -> Unit
) {
    var isDismiss = remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var grupName by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val groupState by groupSettingsViewModel.groupSettingsState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    if(groupState.groupData != null){
        grupName = groupState.groupData?.groupDtl?.nm_grup!!
        desc = groupState.groupData?.groupDtl?.desc ?: ""
    }

    val context = LocalPlatformContext.current
    var imgFile by remember { mutableStateOf(ByteArray(0)) }
    var nameImg by remember { mutableStateOf("") }
    val launcher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    imgFile = it.readByteArray(context)
                    nameImg = it.getName(context)!!

                }
            }
        }
    )
    val imagesName = groupState.groupData?.groupDtl?.img_grup

    if(groupState.isSuccess && !groupState.isLoading){
        setToast("Data Telah Berhasil Diperbaharui !")
    }

    if(groupState.isLoading){
        popUpLoading(isDismiss)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        backgroundColor = Color(0xFFF1F1F1),
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = {
                    Text(
                        "Pengaturan Grup"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navToBack()
                        },
                        content = {
                            Image(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    )
                }

            )
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Box(
                modifier = Modifier.size(99.dp)
                    .background(color = Color(0xFF78A1D7), shape = CircleShape).clickable(
                        interactionSource = interactionSource,
                        indication = ripple(bounded = false)
                    ) {
                        launcher.launch()
                    },
            ){
                if(imagesName != null && imgFile.isEmpty()){
                    AsyncImage(
                        model = imagesName,
                        modifier = Modifier.size(99.dp).clip(CircleShape),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    when(imgFile.isNotEmpty()){
                        true -> {
                            AsyncImage(
                                model = imgFile,
                                modifier = Modifier.size(99.dp).clip(CircleShape),
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop
                            )
                        }
                        false -> {
                            Text(
                                profileNameInitials(groupState.groupData?.groupDtl?.nm_grup ?: "JUD"),
                                style = MaterialTheme.typography.body1,
                                fontSize = 36.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .background(color = Color(0xFF195389), shape = CircleShape)
                        .size(24.dp)
                ){
                    Image(
                        Icons.Default.Edit,
                        "",
                        modifier = Modifier.size(8.57.dp).align(Alignment.Center),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

            }
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Box(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                BasicTextField(
                    value = grupName,
                    onValueChange = {
                        if(groupState.groupData != null) {
                            groupState.groupData?.groupDtl?.nm_grup = it
                        }

                        grupName = it
                    },
                    modifier = Modifier.padding(0.dp).onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                    decorationBox = { innerTextField ->
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Box {
                                    if (grupName.isEmpty()) {
                                        Text(
                                            text = "Isi Nama Grup Ini",
                                            style =  MaterialTheme.typography.caption.copy(color = Color(0xFFABABAB))
                                        )
                                    }
                                    innerTextField()
                                }
                                Image(
                                    painterResource(Res.drawable.group_ic),"", colorFilter = ColorFilter.tint(secondaryColor)
                                )
                            }

                            Divider(
                                color = secondaryColor,
                                thickness = 2.dp,
                                modifier = Modifier.padding(top = 6.dp) // Adjust the position
                            )
                        }
                    },
                    cursorBrush = SolidColor(secondaryColor),
                    textStyle = MaterialTheme.typography.button.copy(
                        color = secondaryColor
                    ),
                    singleLine = false,
                )
            }
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            BasicTextField(
                value = desc,
                onValueChange = {
                    if(groupState.groupData != null) {
                        groupState.groupData?.groupDtl?.desc = it
                    }
                    desc = it
                },
                textStyle = MaterialTheme.typography.caption.copy(color = secondaryColor),
                singleLine = false,
                modifier = Modifier.fillMaxWidth().height(269.dp).padding(horizontal = 16.dp),
                cursorBrush = SolidColor(secondaryColor),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            ){
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    border = {
                        TextFieldDefaults.BorderBox(
                            enabled = true,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.textFieldColors(
                                unfocusedIndicatorColor = Color.Black,
                            ),
                            shape = RoundedCornerShape(9.dp),
                            isError = false,
                            unfocusedBorderThickness = 1.dp,
                        )
                    },
                    value = desc,
                    innerTextField = it,
                    enabled = true,
                    singleLine = false,
                    interactionSource = interactionSource,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            "Silahkan Tulis Deskripsi Disini....",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.caption.copy(color = Color(0xFFABABAB)),
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Black
                    ),
                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                        start = 16.dp
                    )
                )
            }
            Spacer(
                modifier = Modifier.height(22.dp)
            )
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                    groupSettingsViewModel.updateGroupData(groupState.groupData?.groupDtl?.id.toString(), AddGroupRequest(grupName,desc),imgFile,nameImg)
                    scope.launch {
                        keyboardController?.hide()
                    }
                },
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                content = {
                    Text(
                        "Update Grup",
                        style = MaterialTheme.typography.h6,
                        color = fontColor1
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            SettingsListHolder(
                "Edit Role Anggota",
                prefixTitle = "${groupState.groupData?.totalRole} Posisi",
                {
                    navToEditRole(groupState.groupId!!)
                }
            )
            SettingsListHolder(
                "Tambahkan Pertanyaan Keamanan Grup",
                onClick =  {

                }
            )
        }
    }
}