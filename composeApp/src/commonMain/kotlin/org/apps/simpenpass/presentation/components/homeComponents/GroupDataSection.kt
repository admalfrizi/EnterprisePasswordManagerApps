package org.apps.simpenpass.presentation.components.homeComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.presentation.ui.main.home.HomeState
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun GroupDataSection(
    homeState: HomeState,
    navigateToGrupDtl:(String, String)-> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Dari Grup Anda",
            style = MaterialTheme.typography.body2,
            color = secondaryColor,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        )
        Spacer(
            modifier = Modifier.height(11.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().heightIn(
                max = checkDataForHeight(homeState)
            ),
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            userScrollEnabled = false
        ){
            items(homeState.passDataGroupRecommendList){ item ->
                Card(
                    modifier = Modifier.width(166.dp).weight(1f).clickable {
                        navigateToGrupDtl(item.groupId.toString(),item.id.toString())
                    },
                    backgroundColor = Color(0xFF192E49),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
                    ) {
                        Text(
                            item.accountName,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                        Text(
                            item.email,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )
                        Text(
                            item.originGroup,
                            style = MaterialTheme.typography.subtitle1,
                            color = fontColor1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

fun checkDataForHeight(homeState: HomeState): Dp {
    return if (homeState.passDataGroupRecommendList.isEmpty()) {
        120.dp
    } else if(homeState.passDataGroupRecommendList.toString().isNotEmpty()) {
        (homeState.passDataGroupRecommendList.size * 120).dp
    }  else {
        0.dp
    }
}
