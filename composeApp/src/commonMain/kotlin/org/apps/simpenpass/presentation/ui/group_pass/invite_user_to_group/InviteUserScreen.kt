package org.apps.simpenpass.presentation.ui.group_pass.invite_user_to_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.InviteUserToJoinGroup
import org.apps.simpenpass.models.request.SendEmailRequest
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_ic
import resources.delete_ic
import resources.group_add_ic

@OptIn(InternalVoyagerApi::class)
@Composable
fun InviteUserScreen(
    navToBack: () -> Unit,
    inviteUserViewModel: InviteUserViewModel = koinViewModel()
) {
    val inviteState = inviteUserViewModel.inviteUserState.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    val listAddEmail = remember { mutableStateListOf<SendEmailRequest>() }
    var listEmailToSend = remember { mutableStateListOf<SendEmailRequest>() }
    val isDismiss = remember { mutableStateOf(true) }

    if(inviteState.value.isLoading && !sheetState.isVisible){
        popUpLoading(isDismiss)
    }

    if(!inviteState.value.isLoading && inviteState.value.isSuccess && !sheetState.isVisible){
        setToast("Link undangan telah terkirim")
        inviteState.value.isSuccess = false
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
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        sheetContent = {
            EmailAddForm(
                listAddEmail,
                listEmailToSend,
                scope,
                sheetState,
                inviteUserViewModel,
                inviteState.value
            )
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 0.dp,
    ){
        Scaffold(
            backgroundColor = Color(0xFFF1F1F1),
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if(listEmailToSend.isEmpty()){
                            setToast("Data email kosong, silahkan diisi")
                        } else {
                            inviteUserViewModel.sendInviteToEmail(InviteUserToJoinGroup(
                                groupId = inviteState.value.groupId!!,
                                listEmailToInvite = listEmailToSend
                            ))
                        }
                    },
                    backgroundColor = Color(0xFF1E78EE),
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ){
                    Icon(
                        Icons.Default.Check,
                        "",
                        tint = Color.White
                    )
                }
            },
            topBar = {
                TopAppBar(
                    backgroundColor = secondaryColor,
                    title = {
                        Text("Tambah Anggota")
                    },
                    elevation = 0.dp,
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
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ){
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable{
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        elevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(58.dp).background(color = Color(0xFF78A1D7),shape = CircleShape)
                            ){
                                Image(
                                    painterResource(Res.drawable.group_add_ic),
                                    "",
                                    modifier = Modifier.padding(8.dp)
                                )
                                Box(
                                    Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(color = Color(0xFF195389), shape = CircleShape)
                                        .size(20.dp)
                                ){
                                    Image(
                                        painterResource(Res.drawable.add_ic),
                                        "",
                                        modifier = Modifier.size(8.57.dp).align(Alignment.Center)
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.width(47.dp)
                            )
                            Text(
                                "Tambahkan Data Email",
                                style = MaterialTheme.typography.body1,
                                color = secondaryColor
                            )

                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                }

                if(listEmailToSend.isEmpty()){
                    item {
                        EmptyWarning(
                            modifier = Modifier.fillMaxWidth(),
                            warnTitle = "Tidak ada email yang ingin dikirimkan",
                            warnText = "Silahkan Tambah Email yang dikirimkan Link Undangan Grup"
                        )
                    }
                }

                if(listEmailToSend.isNotEmpty()){
                    items(listEmailToSend) { item ->
                        Box(
                            modifier = Modifier.fillMaxWidth().background(Color.White)
                        ){
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    item.email,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.body1.copy(
                                        color = secondaryColor
                                    )
                                )
                                Spacer(
                                    modifier = Modifier.width(24.dp)
                                )
                                IconButton(
                                    onClick = {
                                        listEmailToSend.remove(item)
                                    },
                                    content = {
                                        Image(
                                            painterResource(Res.drawable.delete_ic),""
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmailAddForm(
    listAddEmail: MutableList<SendEmailRequest>,
    listEmailToSend: MutableList<SendEmailRequest>,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    inviteUserViewModel: InviteUserViewModel,
    inviteUserState: InviteUserState
) {
    var email = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    if(!sheetState.isVisible){
        focusManager.clearFocus()
        email.value = ""
        listAddEmail.clear()
    }

    if(inviteUserState.isFound){
        listAddEmail.add(inviteUserState.findResult!!)
        inviteUserState.isFound = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(vertical = 20.dp).imePadding(),
    ){
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Silahkan Isi Email Tujuan yang Ingin Dikirimkan Link Undangan", modifier = Modifier.weight(1f).fillMaxWidth(), style = MaterialTheme.typography.h6, color = secondaryColor)
                    IconButton(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
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
                    modifier = Modifier.height(9.dp)
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email.value,
                    labelHints = "Isi Email yang Ingin Dikirimkan",
                    leadingIcon = null,
                    onValueChange = {
                        email.value = it
                    }
                )
                Spacer(
                    modifier = Modifier.height(9.dp)
                )
            }
        }

        if(listAddEmail.isEmpty()){
            item {
                EmptyWarning(
                    modifier = Modifier.fillMaxWidth(),
                    warnTitle = "Tidak Ada Data Email",
                    warnText = "Silahkan Cari Email yang ingin dikirimkan Link Undangan Grup"
                )
            }
        }

        if(listAddEmail.isNotEmpty()){
            items(listAddEmail){ item ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ){
                            Text(
                                item.email,
                                style = MaterialTheme.typography.body1.copy(
                                    color = secondaryColor
                                )
                            )
                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )
                            Text(
                                item.userId.toString(),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = secondaryColor
                                )
                            )
                        }
                        IconButton(
                            onClick = {
                                listAddEmail.remove(item)
                            },
                            content = {
                                Image(
                                    painterResource(Res.drawable.delete_ic),""
                                )
                            }
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        inviteUserViewModel.findEmailUser(email.value)
                        focusManager.clearFocus()
                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(btnColor),
                    content = {
                        Text(
                            "Cari",
                            style = MaterialTheme.typography.h6,
                            color = fontColor1
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.height(6.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        listAddEmail.forEach {
                            listEmailToSend.add(it)
                        }
                        focusManager.clearFocus()
                        scope.launch {
                            sheetState.hide()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF81BFDA)),
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
    }
}