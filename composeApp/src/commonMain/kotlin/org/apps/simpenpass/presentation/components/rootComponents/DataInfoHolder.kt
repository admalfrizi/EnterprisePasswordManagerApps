package org.apps.simpenpass.presentation.components.rootComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.maskString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.copy_paste
import resources.visibility_ic
import resources.visibility_non_ic

@Composable
fun DataInfoHolder(
    icon: DrawableResource,
    title: String,
    isPassData : Boolean = false
) {
    var showPassword by remember { mutableStateOf(value = false) }

    Box(
        modifier = Modifier.fillMaxWidth().clickable {

        }
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
            IconButton(
                onClick = {

                }
            ){
                Image(
                    painterResource(Res.drawable.copy_paste),"",
                )
            }


        }
    }
}

fun checkData(data: String, isPassData: Boolean, showPassword: Boolean): String {
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
