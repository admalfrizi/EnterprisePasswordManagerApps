package org.apps.simpenpass.presentation.components.groupComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchLoadingGroup(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(99.dp)
                .background(Color.LightGray, shape = CircleShape),
        )
        Box(
            modifier = Modifier
                .width(83.dp)
                .height(24.dp)
                .background(Color.LightGray),
        )
        Box(
            modifier = Modifier
                .width(103.dp)
                .height(24.dp)
                .background(Color.LightGray),
        )
    }
}