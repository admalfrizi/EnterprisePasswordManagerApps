package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.presentation.ui.main.group.GroupState
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.edit_anggota_ic
import resources.email_ic
import resources.whatsapp_ic

@Composable
fun MemberGroupScreen(
    navController: NavController,
    groupState: GroupState,
    groupId: String,
) {
    val memberData = groupState.memberGroupData

    Column {
        EditAnggotaBtnHolder {
            navController.navigate(Screen.EditAnggota.groupId(groupId))
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        if(groupState.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        if(memberData.isNotEmpty()){
            LazyColumn {
                items(memberData){ item ->
                    AnggotaDataHolder(item!!)
                }
            }
        }
    }
}

@Composable
fun AnggotaDataHolder(item: MemberGroupData) {
    Box(modifier = Modifier.fillMaxWidth().background(color = Color.White)){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    item.nama_anggota,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row {
                    Box(modifier = Modifier.size(35.dp).background(color = Color(0xFF78A1D7),shape = CircleShape)) {

                    }
                    Spacer(modifier = Modifier.width(19.dp))
                    Column {
                        Text(
                            item.email_anggota,
                            style = MaterialTheme.typography.subtitle1,
                            color = secondaryColor
                        )
                        Text(
                            "Posisi",
                            style = MaterialTheme.typography.subtitle1,
                            color = secondaryColor
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                if(item.isGroupAdmin!!){
                    Card(
                        backgroundColor = Color(0xFF78A1D7),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "Admin",
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 9.5.dp, vertical = 3.5.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                Row(modifier = Modifier.padding(vertical = if(item.isGroupAdmin!!) 0.dp  else 19.dp)) {
                    Image(
                        painterResource(Res.drawable.whatsapp_ic),
                        "",
                        modifier = Modifier.clickable {  }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painterResource(Res.drawable.email_ic),
                        "",
                        modifier = Modifier.clickable {  }
                    )
                }

            }
        }

    }
}

@Composable
fun EditAnggotaBtnHolder(onClick: () -> Unit) {
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
                    painter = painterResource(Res.drawable.edit_anggota_ic),
                    "",
                    modifier = Modifier.padding(8.dp)
                )

            }
            Spacer(
                modifier = Modifier.width(28.dp)
            )
            Text(
                "Edit Anggota",
                style = MaterialTheme.typography.h6,
                fontSize = 13.sp,
                color = secondaryColor
            )
        }
    }
}
