package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
import org.apps.simpenpass.presentation.components.groupDtlComponents.GroupDtlLoadShimmer
import org.apps.simpenpass.presentation.components.groupDtlComponents.OptionAddData
import org.apps.simpenpass.presentation.components.groupDtlComponents.TopBarDtl
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.Constants
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.edit_ic
import resources.group_ic
import resources.your_data_ic

@Composable
fun GroupPassDetail(
    navController: NavController,
    navToBack: () -> Unit,
    groupViewModel: GroupDetailsViewModel = koinViewModel(),
    bottomEdgeColor: MutableState<Color>
) {
    val tabsName = listOf("Password", "Anggota")
    val groupState by groupViewModel.groupDtlState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    val itemsData = listOf(
        MethodSelection(1, Res.drawable.edit_ic, " Buat Data Baru"),
        MethodSelection(2, Res.drawable.your_data_ic, "Ambil Dari Data Anda"),
    )
    var isPopUp = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    bottomEdgeColor.value = Color(0xFFF1F1F1)

    if(sheetState.isVisible){
        bottomEdgeColor.value = Color.White
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            OptionAddData(scope,sheetState,itemsData,navController)
        }
    ){
        ContentView(
            navController,
            navToBack,
            snackBarHostState,
            tabsName,
            sheetState,
            scope,
            groupState,
            groupViewModel,
            isPopUp
        )
    }
}


@Composable
fun ContentView(
    navController: NavController,
    navToBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    tabsName: List<String>,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupDetailsState,
    groupViewModel : GroupDetailsViewModel,
    isPopUp : MutableState<Boolean>
) {
    var indexTab by rememberSaveable { mutableStateOf(0) }
    val imagesName = groupState.dtlGroupData?.img_grup
    val urlImages = "${Constants.IMAGE_URL}groupProfile/$imagesName"

    if(isPopUp.value) {
        EditGroupDialog(
            onDismissRequest = {
                isPopUp.value = false

                if(!isPopUp.value){
                    groupViewModel.getDetailGroup(groupState.groupId!!)
                }
            },
            isPopUp,
            snackbarHostState,
            scope,
            urlImages,
            groupViewModel,
            imagesName,
            groupState,
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        backgroundColor = Color(0xFFF1F1F1),
        topBar = {
            TopBarDtl(
                navToBack,
                groupViewModel,
                popUpEditGroup = {
                    isPopUp.value = true
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(secondaryColor)
                            .height(43.dp)
                    )

                    if(groupState.isLoading){
                        GroupDtlLoadShimmer()
                    }

                    if(!groupState.isLoading && groupState.dtlGroupData != null){
                        Row(
                            modifier = Modifier.padding(start = 16.dp, end= 16.dp, top = 22.dp).fillMaxWidth()
                                .align(
                                    Alignment.BottomStart
                                )
                        ) {
                            Box(
                                modifier = Modifier.size(99.dp)
                                    .background(color = Color(0xFF78A1D7), shape = CircleShape).clip(CircleShape)
                            ){
                                if(imagesName != null){
                                    AsyncImage(
                                        model = urlImages,
                                        modifier = Modifier.size(99.dp),
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text(
                                        text = profileNameInitials(groupState.dtlGroupData.nm_grup),
                                        style = MaterialTheme.typography.body1,
                                        fontSize = 36.sp,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.width(38.dp))
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    groupState.dtlGroupData.nm_grup,
                                    style = MaterialTheme.typography.button,
                                    color = secondaryColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    groupState.dtlGroupData.desc ?: "",
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                        }
                    }

                }
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Card(
                        modifier = Modifier.width(167.dp).height(93.dp).weight(1f),
                        backgroundColor = Color(0xFF1E559C),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(11.dp).fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Jumlah Data Password",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White
                            )
                            Text(
                                "23",
                                style = MaterialTheme.typography.body2,
                                fontSize = 24.sp
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.width(9.dp)
                    )
                    Card(
                        modifier = Modifier.width(167.dp).height(93.dp).weight(1f),
                        backgroundColor = Color(0xFF1E559C),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(11.dp).fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Jumlah Anggota Grup",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White
                            )
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                            Text(
                                groupState.memberGroupData.size.toString(),
                                style = MaterialTheme.typography.body2,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                TabRow(
                    selectedTabIndex = indexTab,
                    backgroundColor = Color.White,
                    contentColor = secondaryColor,
                ) {
                    tabsName.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    title,
                                    style = MaterialTheme.typography.body1,
                                )
                            },
                            onClick = {
                                indexTab = index
                            },
                            selected = indexTab == index, selectedContentColor = secondaryColor
                        )
                    }
                }
                when (indexTab) {
                    0 -> {
                        PassDataScreen(navController,sheetState,scope,groupState,groupViewModel)
                    }

                    1 -> {
                        MemberGroupScreen(navController,groupState)
                    }
                }
            }
        }
    )
}

@Composable
fun ListOptionHolder(icon: DrawableResource, title: String,isSelected : Boolean, onSelected : () -> Unit) {
    Card(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth().clickable {
            onSelected()
        },
        backgroundColor = if(isSelected) Color(0xFF78A1D7) else Color.Transparent,
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFF264A97))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                "",
                colorFilter = ColorFilter.tint(if(isSelected) Color.White else Color(0xFF264A97))
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.body1,
                color = if(isSelected) Color.White else Color(0xFF264A97)
            )
        }
    }
}

data class MethodSelection(
    val id: Int,
    val icon: DrawableResource,
    val title: String
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditGroupDialog(
    onDismissRequest: () -> Unit,
    isPopUp: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState,
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
        grupName = groupState.dtlGroupData.nm_grup
        desc = groupState.dtlGroupData.desc ?: ""
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

    if(groupState.isUpdated && !groupState.isLoading){
        isPopUp.value = false
        scope.launch {
            snackbarHostState.showSnackbar("Data Grup telah Diperbaharui")
        }
    }

    if(groupState.isLoading){
        popUpLoading(isDismiss)
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
                                    text = profileNameInitials(groupState.dtlGroupData?.nm_grup!!),
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
                                groupState.dtlGroupData.nm_grup = it
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
                            groupState.dtlGroupData.desc = it
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
                        scope.launch {
                            keyboardController?.hide()
                        }
                        groupViewModel.updateGroupData(groupState.dtlGroupData?.id.toString(), AddGroupRequest(grupName,desc),imgFile,nameImg)
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