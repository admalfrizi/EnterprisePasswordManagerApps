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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.components.ConnectionWarning
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.groupComponents.AddGroupHolder
import org.apps.simpenpass.presentation.components.groupComponents.ListGroupHolder
import org.apps.simpenpass.style.secondaryColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GroupScreen(
    groupViewModel: GroupViewModel = koinViewModel(),
    navigateToGrupDtl : (String) -> Unit,
    sheetState: ModalBottomSheetState,
) {
    val groupState by groupViewModel.groupState.collectAsStateWithLifecycle()
    val isConnected by groupViewModel.isConnected.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
      backgroundColor = Color(0xFFF1F1F1),
      content = {
          Column(
              modifier = Modifier.fillMaxWidth()
          ) {
              AddGroupHolder(
                  onClick = {
                      scope.launch {
                          sheetState.show()
                      }

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

              if(!isConnected){
                  Box(
                      modifier = Modifier.fillMaxSize(),
                      contentAlignment = Alignment.Center,
                  ) {
                      ConnectionWarning(
                          modifier = Modifier.fillMaxSize(),
                          warnTitle = "Internet Anda Telah Teputus !",
                          warnText = "Silahkan untuk memeriksa koneksi internet anda dan coba untuk refresh kembali halaman ini",
                      )
                  }
              }

              if(groupState.isLoading){
                  Box(
                      modifier = Modifier.fillMaxSize(),
                      contentAlignment = Alignment.Center,
                  ) {
                      CircularProgressIndicator()
                  }
              }

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
                          ListGroupHolder(item, { navigateToGrupDtl(item?.id.toString()) })
                      }
                  }
              }
          }
      }
  )
}

