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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun GroupDtlLoadShimmer() {
    Row(
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .padding(start = 16.dp, end= 16.dp,top = 22.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .size(99.dp)
                .background(Color.LightGray, shape = CircleShape),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(Color.LightGray),
            )
            Box(
                modifier = Modifier
                    .size(120.dp, 20.dp)
                    .background(Color.LightGray),
            )
        }
    }
}