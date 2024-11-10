package org.apps.simpenpass.presentation.components.addGroupComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.apps.simpenpass.utils.setToast

@Composable
fun ResultSearchMemberView(
    modifier: Modifier,
    resultSearchData: List<UserData>,
    memberList: List<UserData>,
    listAdd: MutableList<UserData>,
    sheetState: ModalBottomSheetState
) {
    LazyColumn(
        modifier = modifier,
    ){
       items(resultSearchData){ item ->

           if(memberList.contains(item) && sheetState.isVisible){
               setToast("Pengguna Sudah Bergabung Pada Grup Ini")
           }

           Box(
               modifier = Modifier.fillMaxWidth()
                   .background(
                       if(listAdd.contains(item) || memberList.contains(item))
                           Color(0xFFC0D8F6)
                       else
                           Color(0xFFF1F1F1)
                   ).clickable{
                       if(!listAdd.contains(item) && !memberList.contains(item)){
                           listAdd.add(item)
                       }
                   },
               contentAlignment = Alignment.CenterStart
           ){
               Row(
                   modifier = Modifier.padding(start = 16.dp,top = 9.dp, bottom = 9.dp),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(18.dp)
               ) {
                   Box(
                       modifier = Modifier.size(44.dp)
                           .background(color = Color(0xFF78A1D7), shape = CircleShape).clip(CircleShape)
                   ){
                       Text(
                           text = profileNameInitials(item.name),
                           style = MaterialTheme.typography.body1,
                           fontSize = 18.sp,
                           color = Color.White,
                           modifier = Modifier.align(Alignment.Center)
                       )
                   }
                   Column {
                       Text(
                           item.name,
                           style = MaterialTheme.typography.body1.copy(
                               color = secondaryColor
                           )
                       )
                       Spacer(
                           modifier = Modifier.height(5.dp)
                       )
                       Text(
                           item.email,
                           style = MaterialTheme.typography.subtitle1.copy(
                               color = secondaryColor
                           )
                       )
                   }
               }
               if(listAdd.contains(item) || memberList.contains(item)){
                   Image(
                       Icons.Default.Check,
                       "",
                       modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp),
                       colorFilter = ColorFilter.tint(color = secondaryColor)
                   )
               }
           }

       }
    }
}