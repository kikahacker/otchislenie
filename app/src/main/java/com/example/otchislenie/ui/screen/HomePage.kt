package com.example.otchislenie.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.otchislenie.R

@Preview
@Composable
fun HomePageContent(){
    val sizeIcons = 60.dp
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(color = Color(0xFF7AC793))
                .statusBarsPadding().padding(top = 10.dp, start = 10.dp) // Учитываем отступ под статус-бар
        ) {
            IconButton(onClick = {}, modifier = Modifier.fillMaxHeight().width(40.dp)) {
                Icon(
                    painter = painterResource(R.drawable.free_icon_sort_descending_8189398),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp)
                    .padding(end = 20.dp, start = 20.dp, top = 15.dp)
            ) {
                Text(
                    text = "Spendly",
                    fontFamily = FontFamily(Font(R.font.aguafina_script)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxHeight()
                    .width(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.loupe),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxHeight()
                    .width(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.exchange),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxHeight()
                    .width(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.dots),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 30.dp, start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.free_icon_house_5687455),
                    contentDescription = null
                )
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Green)
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_dumbbell_4205515), contentDescription = null)
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color(0xFFFFA500))
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_cocktail_259953), contentDescription = null)
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color(0xFF8A2BE2))
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null, modifier = Modifier.padding(3.dp))
            }
        }
        Box(modifier = Modifier.padding(top = 40.dp)){
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().height(300.dp).padding(start = 15.dp)) {
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().height(300.dp).padding(end = 15.dp)) {
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ },
                    modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
                ) {
                    Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 30.dp, start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.free_icon_car_5670285),
                    contentDescription = null
                )
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(sizeIcons),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon(painter = painterResource(R.drawable.free_icon_car_5670285), contentDescription = null)
            }
        }
    }
}