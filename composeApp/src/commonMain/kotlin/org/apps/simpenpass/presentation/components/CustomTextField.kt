package org.apps.simpenpass.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.visibility_ic
import resources.visibility_non_ic

@Composable
fun CustomTextField(
    modifier: Modifier,
    value: String,
    focusColor: Color,
    isFocus : Boolean,
    isPassword: Boolean = false,
    onValueChange : (String) -> Unit,
    labelHints : String,
    leadingIcon : @Composable() (() -> Unit)?,
    interactionSource: MutableInteractionSource
) {

    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        singleLine = true,
        interactionSource = interactionSource,
        modifier = modifier,
        value = value ,
        textStyle = MaterialTheme.typography.subtitle1,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = labelHints ,
                color = Color(0xFF9E9E9E),
                style = MaterialTheme.typography.subtitle1
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = if (isFocus) focusColor else Color.Transparent,
            cursorColor = Color(0xFF384A92),
            textColor = Color(0xFF384A92)
        ),
        visualTransformation = if(isPassword){
            if(showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if(isPassword) KeyboardType.Password else KeyboardType.Text),
        shape = RoundedCornerShape(10.dp),
        leadingIcon = leadingIcon,
        trailingIcon = {
            if(isPassword){
                if(showPassword){
                    IconButton(onClick = { showPassword = false }) {
                        Image(
                            painterResource(Res.drawable.visibility_ic),
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Image(
                            painterResource(Res.drawable.visibility_non_ic),
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
        }
    )
}