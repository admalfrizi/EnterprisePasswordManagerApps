package org.apps.simpenpass.presentation.ui.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.apps.simpenpass.presentation.components.homeComponents.DataPassHolder
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.arrow_right_ic
import resources.menu_ic

@Composable
fun HomeContentView(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Yang Sering Dipakai",
                    style = MaterialTheme.typography.body2,
                    color = secondaryColor
                )
                Image(
                    painterResource(Res.drawable.arrow_right_ic),
                    ""
                )
            }
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Di Grup Anda",
                    style = MaterialTheme.typography.body2,
                    color = secondaryColor
                )
                Image(
                    painterResource(Res.drawable.arrow_right_ic),
                    ""
                )
            }
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.width(166.dp).weight(1f),
                    backgroundColor = Color(0xFF192E49),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
                    ) {
                        Text(
                            "Nama Akun",
                            style = MaterialTheme.typography.body1,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                        Text(
                            "Email",
                            style = MaterialTheme.typography.subtitle1,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )
                        Text(
                            "Dari Grup Apa",
                            style = MaterialTheme.typography.subtitle1,
                            color = fontColor1,
                            fontSize = 10.sp
                        )
                    }

                }
                Spacer(
                    modifier = Modifier.width(11.dp)
                )
                Card(
                    modifier = Modifier.width(166.dp).weight(1f),
                    backgroundColor = Color(0xFF192E49),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
                    ) {
                        Text(
                            "Nama Akun",
                            style = MaterialTheme.typography.body1,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                        Text(
                            "Email",
                            style = MaterialTheme.typography.subtitle1,
                            color = fontColor1
                        )
                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )
                        Text(
                            "Dari Grup Apa",
                            style = MaterialTheme.typography.subtitle1,
                            color = fontColor1,
                            fontSize = 10.sp
                        )
                    }

                }
            }
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ){
                Text(
                    "Data Anda",
                    style = MaterialTheme.typography.body2,
                    color = secondaryColor
                )
            }
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            DataPassHolder("Nama Akun", "Email")
            Spacer(
                modifier = Modifier.height(7.dp)
            )
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(color = Color(0xFF8CC4FB), width = 1.dp),
                onClick = {navController.navigate(Screen.ListPassDataUser.route)},
                content = {
                    Text(
                        "Lihat Lebih Banyak",
                        style = MaterialTheme.typography.subtitle2,
                        color = secondaryColor
                    )
                }
            )
        }
    }
}