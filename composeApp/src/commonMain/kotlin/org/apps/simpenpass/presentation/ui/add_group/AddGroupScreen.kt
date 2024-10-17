package org.apps.simpenpass.presentation.ui.add_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.components.addGroupComponents.BottomSheetContent
import org.apps.simpenpass.presentation.components.addGroupComponents.ContentType
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_description_ic
import resources.add_pic_ic
import resources.add_role_group_ic
import resources.edit_anggota_ic
import resources.edit_group_name_ic

@OptIn(InternalVoyagerApi::class)
@Composable
fun AddGroupScreen(navController: NavController) {
    var grupName by rememberSaveable { mutableStateOf("") }
    var currentBottomSheet: ContentType? by remember { mutableStateOf(null) }
    val desc = remember { mutableStateOf("") }
    val findMember = remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }
    val isLoading = remember { mutableStateOf(false) }
    val isDismiss = remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    var byteArray by remember { mutableStateOf(ByteArray(0)) }

    if(isLoading.value){
        popUpLoading(isDismiss)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val underlineColor = if (isFocused) Color.White else Color.Transparent
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current
    val launcher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    byteArray = it.readByteArray(context)
                }
            }
        }
    )

    if(sheetState.isVisible){
        focusManager.clearFocus()
    }

    BackHandler(
        enabled = sheetState.isVisible,
        onBack = {
            scope.launch {
                sheetState.hide()
            }
        }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            currentBottomSheet?.let {
                BottomSheetContent(
                    contentType = it,
                    scope = scope,
                    sheetState = sheetState,
                    keyboardController = keyboardController!!,
                    desc,
                    findMember,
                    interactionSource = interactionSource
                )
            }
        }
    ){
        Scaffold(
            backgroundColor = Color(0xFFF1F1F1),
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Color(0xFF1E78EE),
                    onClick = {
                        isLoading.value = true
                        navController.navigateUp()
                    },
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ){
                    Icon(
                        Icons.Default.Check,
                        "",
                        tint = Color.White
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            topBar = {
                TopAppBar(
                    backgroundColor = secondaryColor,
                    elevation = 0.dp,
                    actions = {

                    },
                    title = {
                        Text(
                            "Menambah Data Grup"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
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
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(secondaryColor, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.background(Color(0xFF78A1D7), shape = CircleShape).size(100.dp).clickable(
                                interactionSource = interactionSource,
                                indication = ripple(bounded = false)
                            ) {
                                launcher.launch()
                            }.clip(CircleShape)
                        ) {
                            if(byteArray.isNotEmpty()){
                                AsyncImage(
                                    model = byteArray,
                                    modifier = Modifier.size(100.dp),
                                    contentDescription = "Profile Picture",
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painterResource(Res.drawable.add_pic_ic),
                                    "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.padding(18.dp)
                                )
                            }

                        }
                        Spacer(
                            modifier = Modifier.width(18.dp)
                        )
                        Box(
                            modifier = Modifier.padding(start = 0.dp).fillMaxWidth()
                        ) {
                            BasicTextField(
                                value = grupName,
                                onValueChange = {
                                    grupName = it
                                },
                                modifier = Modifier.padding(0.dp).onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                },
                                decorationBox = { innerTextField ->
                                    Column {
                                        // Draw the actual text field content
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box {
                                                if (grupName.isEmpty()) {
                                                    Text(
                                                        text = "Isi Nama Grup Ini",
                                                        style =  MaterialTheme.typography.caption.copy(color = Color(0xFFABABAB))
                                                    )
                                                }
                                                innerTextField()
                                            }

                                            if (grupName.isEmpty()) {
                                                Image(
                                                    painterResource(Res.drawable.edit_group_name_ic),""
                                                )
                                            }
                                        }
                                        Divider(
                                            color = underlineColor,
                                            thickness = 2.dp,
                                            modifier = Modifier.padding(top = 6.dp) // Adjust the position
                                        )
                                    }
                                },
                                cursorBrush = SolidColor(Color.White),
                                textStyle = MaterialTheme.typography.caption.copy(color = Color.White),
                                singleLine = false,
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(17.dp)
                )
                BtnHolder({
                    currentBottomSheet = ContentType.ADD_DESC
                    scope.launch {
                        sheetState.show()
                    }
                },"Isi Deskripsi Grup",Res.drawable.add_description_ic)
                BtnHolder({
                    currentBottomSheet = ContentType.ADD_MEMBER
                    scope.launch {
                        sheetState.show()
                    }
                },"Tambah Anggota Baru", Res.drawable.edit_anggota_ic)
                BtnHolder({ navController.navigate(Screen.EditRole.route) },"Tambah Role Grup",Res.drawable.add_role_group_ic)
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Text(
                    "Anggota Grup",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Box(modifier = Modifier.fillMaxWidth().background(color = Color.White)){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(47.dp).background(color = Color(0xFF78A1D7),shape = CircleShape)
                        ){
                            Text(
                                text = profileNameInitials("Armando Vereira"),
                                style = MaterialTheme.typography.body1,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(
                            modifier = Modifier.width(25.dp)
                        )
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Armando Vereira",
                                style = MaterialTheme.typography.body1,
                                color = secondaryColor
                            )
                            Text(
                                "Email",
                                style = MaterialTheme.typography.subtitle1,
                                color = secondaryColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BtnHolder(onClick: () -> Unit, titleAction: String, drawable: DrawableResource) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
           onClick()
        }
    ){
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(55.dp).background(color = Color(0xFF78A1D7),shape = RoundedCornerShape(7.dp))
            ) {
                Image(
                    painter = painterResource(drawable),
                    "",
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(
                modifier = Modifier.width(28.dp)
            )
            Text(
                titleAction,
                style = MaterialTheme.typography.h6,
                fontSize = 13.sp,
                color = secondaryColor
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddDescSection(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController?,
    desc: MutableState<String>,
    interactionSource: MutableInteractionSource
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp).fillMaxWidth().imePadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Isi Deskripsi Grup", modifier = Modifier.weight(1f).fillMaxWidth(), style = MaterialTheme.typography.h6, color = secondaryColor)
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        keyboardController?.hide()
                    }
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
            modifier = Modifier.height(19.dp)
        )
        BasicTextField(
            value = desc.value,
            onValueChange = {
                desc.value = it
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
                value = desc.value,
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
                scope.launch {
                    sheetState.hide()
                    desc.value = ""
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
            content = {
                Text(
                    "Tambahkan",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
    }
}

@Composable
fun AddMemberSection(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    findMember: MutableState<String>,
    keyboardController: SoftwareKeyboardController?
){
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp).fillMaxWidth().imePadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Silahkan Tambahkan Anggota Baru",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                color = secondaryColor
            )
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        keyboardController?.hide()
                    }
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
            modifier = Modifier.height(19.dp)
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            value = findMember.value ,
            textStyle = MaterialTheme.typography.subtitle1,
            onValueChange = {
                findMember.value = it
            },
            placeholder = {
                Text(
                    text = "Cari Anggota Baru Disini..." ,
                    color = Color(0xFF9E9E9E),
                    style = MaterialTheme.typography.subtitle1
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = secondaryColor,
                unfocusedIndicatorColor = Color.Black,
                cursorColor = Color(0xFF384A92),
                textColor = Color(0xFF384A92)
            ),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = null,
        )
        Spacer(
            modifier = Modifier.height(19.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    sheetState.hide()
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
            content = {
                Text(
                    "Tambahkan Anggota",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
    }
}
