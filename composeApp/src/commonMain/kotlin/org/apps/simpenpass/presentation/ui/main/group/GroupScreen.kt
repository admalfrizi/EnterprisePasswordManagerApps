package org.apps.simpenpass.presentation.ui.main.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.groupComponents.AddGroupHolder
import org.apps.simpenpass.presentation.components.groupComponents.ListGroupHolder
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GroupScreen(
    navController: NavController,
    groupViewModel: GroupViewModel = koinViewModel()
) {

    val groupState by groupViewModel.groupState.collectAsState()

//    val groupList by remember { mutableStateOf(emptyList<GrupPassData>()) }

    Scaffold(
      backgroundColor = Color(0xFFF1F1F1),
      topBar = {
          TopAppBar(
              backgroundColor = secondaryColor,
              elevation = 0.dp,
              title = {
                  Text(
                      "Grup Data Password",
                      style = MaterialTheme.typography.h6,
                      color = fontColor1
                  )
              }
          )
      },
      content = {
          Column(
              modifier = Modifier.fillMaxWidth()
          ) {
              AddGroupHolder(
                  onClick = {
                      navController.navigate(Screen.AddGroupPass.route)
                  }
              )
              Spacer(
                  modifier = Modifier.height(13.dp)
              )
              Text(
                  "Daftar Grup",
                  style = MaterialTheme.typography.body1,
                  color = secondaryColor,
                  modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
              )
              Spacer(
                  modifier = Modifier.height(9.dp)
              )
              if(groupState.groupData.isEmpty()){
                  Box(
                      modifier = Modifier.fillMaxSize(),
                      contentAlignment = Alignment.Center,
                  ) {
                      EmptyWarning(
                          modifier = Modifier.fillMaxWidth(),
                          warnTitle = "Anda belum bergabung dengan grup !",
                          warnText = "Silahkan buat grup baru dengan mengklik tombol di atas",
                      )
                  }
              } else {
                  LazyColumn(
                      modifier = Modifier.fillMaxWidth(),
                  ) {
                      items(groupState.groupData) { item ->
                          ListGroupHolder(navController, item)
                      }
                  }
              }
//              ListGroupHolder(navController,"Nama Grup")
//              ListGroupHolder(navController, "Nama Grup")
          }
      }
  )
}

