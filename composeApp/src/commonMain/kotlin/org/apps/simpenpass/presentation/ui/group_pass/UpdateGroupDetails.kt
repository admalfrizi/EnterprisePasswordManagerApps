package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.group_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateGroupDetails(
    onDismissRequest: () -> Unit,
    isPopUp: MutableState<Boolean>,
    scope: CoroutineScope,
    urlImages: String,
    groupViewModel: GroupDetailsViewModel,
    imagesName: String?,
    groupState: GroupDetailsState
) {
    val isDismiss = remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var grupName by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    if(groupState.dtlGroupData != null){
        grupName = groupState.dtlGroupData.groupDtl.nm_grup
        desc = groupState.dtlGroupData.groupDtl.desc ?: ""
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

    if(groupState.isLoading){
        popUpLoading(isDismiss)
    }

    if(groupState.isUpdated && !groupState.isLoading){
        isPopUp.value = false
        grupName = ""
        desc = ""
        groupViewModel.getDetailGroup(groupState.groupId!!)
    }

    Dialog(
        onDismissRequest = {onDismissRequest()},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Edit Data Grup",
                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                    )
                    IconButton(
                        onClick = {
                            onDismissRequest()
                            groupViewModel.getDetailGroup(groupState.groupId!!)
                        },
                        content = {
                            Icon(
                                Icons.Filled.Clear,
                                ""
                            )
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
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
                            model = urlImages,
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
                                    profileNameInitials(groupState.dtlGroupData?.groupDtl?.nm_grup ?: "JUD"),
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
                    modifier = Modifier.padding(start = 0.dp).fillMaxWidth()
                ) {
                    BasicTextField(
                        value = grupName,
                        onValueChange = {
                            if(groupState.dtlGroupData != null) {
                                groupState.dtlGroupData.groupDtl.nm_grup = it
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
                        if(groupState.dtlGroupData != null) {
                            groupState.dtlGroupData.groupDtl.desc = it
                        }
                        desc = it
                    },
                    textStyle = MaterialTheme.typography.caption.copy(color = secondaryColor),
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth().height(269.dp),
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
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        groupViewModel.updateGroupData(groupState.dtlGroupData?.groupDtl?.id.toString(), AddGroupRequest(grupName,desc),imgFile,nameImg)
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
            }
        }
    }

}