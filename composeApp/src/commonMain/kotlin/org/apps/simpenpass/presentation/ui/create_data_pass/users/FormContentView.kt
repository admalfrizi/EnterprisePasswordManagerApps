package org.apps.simpenpass.presentation.ui.create_data_pass.users

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.formComponents.HeaderContainer
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun FormContentView() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.align(Alignment.TopCenter).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            HeaderContainer()
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            FormContent()
        }
    }
}

@Composable
fun FormContent() {
    val interactionSource = remember { MutableInteractionSource() }

    var userAccount by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jnsPass by remember { mutableStateOf("") }
    var passData by remember { mutableStateOf("") }
    var urlPass by remember { mutableStateOf("") }

    var usrAccFocus by remember { mutableStateOf(false) }
    var emailFocus by remember { mutableStateOf(false) }
    var jnsPassFocus by remember { mutableStateOf(false) }
    var passDataFocus by remember { mutableStateOf(false) }
    var urlFocus by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Nama Akun",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> usrAccFocus = focusState.isFocused },
            isFocus = usrAccFocus,
            value = userAccount,
            labelHints = "Isi Nama Akun",
            leadingIcon = null,
            onValueChange = {
                userAccount = it
            },
            interactionSource = interactionSource
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Jenis Password",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> jnsPassFocus = focusState.isFocused },
            isFocus = jnsPassFocus,
            value = jnsPass,
            labelHints = "Isi Jenis Password",
            leadingIcon = null,
            onValueChange = {
                jnsPass = it
            },
            interactionSource = interactionSource
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Email",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> emailFocus = focusState.isFocused },
            isFocus = emailFocus,
            value = email,
            labelHints = "Isi Email",
            leadingIcon = null,
            onValueChange = {
                email = it
            },
            interactionSource = interactionSource
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Password",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> passDataFocus = focusState.isFocused },
            isFocus = passDataFocus,
            value = passData,
            labelHints = "Isi Data Password",
            leadingIcon = null,
            onValueChange = {
                passData = it
            },
            interactionSource = interactionSource
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "URL Website",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> urlFocus = focusState.isFocused },
            isFocus = urlFocus,
            value = urlPass,
            labelHints = "Isi Data URL",
            leadingIcon = null,
            onValueChange = {
                urlPass = it
            },
            interactionSource = interactionSource
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Data Tambahan",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        Row {
            Card(
                modifier = Modifier.width(168.dp).weight(1f),
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Text(
                        "Nama Akun",
                        style = MaterialTheme.typography.body1,
                        color = fontColor1
                    )
                    Spacer(
                        modifier = Modifier.height(26.dp)
                    )
                    Text(
                        "Dari Grup Apa",
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        fontSize = 10.sp
                    )
                }
            }
            Spacer(
                modifier = Modifier.width(7.dp)
            )
            Card(
                modifier = Modifier.width(168.dp).weight(1f),
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Text(
                        "Nama Akun",
                        style = MaterialTheme.typography.body1,
                        color = fontColor1
                    )
                    Spacer(
                        modifier = Modifier.height(26.dp)
                    )
                    Text(
                        "Dari Grup Apa",
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        fontSize = 10.sp
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.height(14.dp)
        )
    }
}