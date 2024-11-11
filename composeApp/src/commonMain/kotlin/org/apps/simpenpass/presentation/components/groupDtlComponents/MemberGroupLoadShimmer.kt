package org.apps.simpenpass.presentation.components.groupDtlComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun MemberGroupLoadShimmer() {
    Box(modifier = Modifier.fillMaxWidth().background(color = Color.White)){
        Row(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth()
                .padding(start = 16.dp, end= 16.dp,top = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .background(Color.LightGray),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .background(Color.LightGray, shape = CircleShape),
                    )
                    Spacer(modifier = Modifier.width(19.dp))
                    Column {
                        Box(
                            modifier = Modifier
                                .width(240.dp)
                                .height(18.dp)
                                .background(Color.LightGray),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .size(120.dp, 18.dp)
                                .background(Color.LightGray),
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier.width(56.dp)
                        .height(18.dp)
                        .background(Color.LightGray,RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(modifier = Modifier.padding(vertical = 19.dp)) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.LightGray, shape = CircleShape),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.LightGray, shape = CircleShape),
                    )
                }

            }
        }
    }

}