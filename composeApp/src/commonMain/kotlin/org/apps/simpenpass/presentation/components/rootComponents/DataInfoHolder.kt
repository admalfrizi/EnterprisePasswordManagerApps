package org.apps.simpenpass.presentation.components.rootComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.copyText
import org.apps.simpenpass.utils.maskString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.copy_paste
import resources.visibility_ic
import resources.visibility_non_ic

@Composable
fun DataInfoHolder(
    warnCopy: () -> Unit,
    icon: DrawableResource? = null,
    title: String,
    isPassData : Boolean = false,
    isLeadIcon : Boolean = true,
    isCopyPaste: Boolean = true
) {
    var showPassword by remember { mutableStateOf(value = false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isLeadIcon && icon != null){
                Image(
                    painterResource(icon),"",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(
                    modifier = Modifier.width(19.dp)
                )
            }
            Text(
                checkData(title, isPassData, showPassword),
                style = MaterialTheme.typography.body2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            if(isPassData){
                if(showPassword){
                    IconButton(
                        onClick = {
                            showPassword = false
                        }
                    ){
                        Image(
                            painterResource(Res.drawable.visibility_ic),"",
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            showPassword = true
                        }
                    ){
                        Image(
                            painterResource(Res.drawable.visibility_non_ic),"",
                        )
                    }
                }
            }
            if(isCopyPaste){
                IconButton(
                    onClick = {
                        copyText(title)
                        warnCopy()
                    }
                ){
                    Image(
                        painterResource(Res.drawable.copy_paste),"",
                    )
                }
            }
        }
    }
}

@Composable
fun LatestDataInfoHolder(
    warnCopy: () -> Unit,
    icon: DrawableResource,
    title: String,
) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(28.dp)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.body2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            IconButton(
                onClick = {
                    copyText(title)
                    warnCopy()
                }
            ){
                Image(
                    painterResource(Res.drawable.copy_paste),"",
                )
            }
        }
    }
}

@Composable
fun PassDataInfoHolder(
    warnCopy: () -> Unit,
    sheetState: ModalBottomSheetState,
    icon: DrawableResource,
    passData: String,
    isPopUp : MutableState<Boolean>,
    isEncrypted: Boolean = false,
) {
    var showPassword by remember { mutableStateOf(value = false) }

    if(!sheetState.isVisible){
        showPassword = false
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(28.dp)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                checkPassMask(passData,showPassword),
                style = MaterialTheme.typography.body2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            IconButton(
                onClick = {
                    if(isEncrypted){
                        isPopUp.value = true
                    } else {
                        showPassword = !showPassword
                    }
                }
            ){
                if(showPassword){
                    Image(
                        painterResource(Res.drawable.visibility_ic),"",
                    )
                } else {
                    Image(
                        painterResource(Res.drawable.visibility_non_ic),"",
                    )
                }
            }
            IconButton(
                onClick = {
                    if(!isEncrypted){
                        copyText(passData)
                    }

                    warnCopy()
                }
            ){
                Image(
                    painterResource(Res.drawable.copy_paste),"",
                )
            }
        }
    }
}

@Composable
fun PassDataGroupInfoHolder(
    warnCopy: () -> Unit,
    icon: DrawableResource,
    passData: String,
    isPopUp : MutableState<Boolean>,
    isEncrypted: Boolean = false,
) {
    var showPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(28.dp)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                checkPassMask(passData,showPassword),
                style = MaterialTheme.typography.body2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            IconButton(
                onClick = {
                    if(isEncrypted){
                        isPopUp.value = true
                    } else {
                        showPassword = !showPassword
                    }
                }
            ){
                if(showPassword){
                    Image(
                        painterResource(Res.drawable.visibility_ic),"",
                    )
                } else {
                    Image(
                        painterResource(Res.drawable.visibility_non_ic),"",
                    )
                }
            }
            IconButton(
                onClick = {
                    if(!isEncrypted){
                        copyText(passData)
                    }

                    warnCopy()
                }
            ){
                Image(
                    painterResource(Res.drawable.copy_paste),"",
                )
            }
        }
    }
}

fun checkPassMask(data: String, showPassword: Boolean): String {
    return if(showPassword){
        data
    }
    else {
        maskString(data)
    }
}

fun checkData(
    data: String,
    isPassData: Boolean,
    showPassword: Boolean
): String {
    return if(isPassData){
        if(showPassword){
            data
        }
        else {
            maskString(data)
        }
    } else {
        data
    }
}
