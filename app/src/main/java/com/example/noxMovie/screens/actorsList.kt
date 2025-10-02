package com.example.noxMovie.screens

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ActorsList(name: String , img: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.padding(5.dp)) {
        AsyncImage(
            model = img,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_menu_info_details),
            modifier = Modifier.size(90.dp).clip(CircleShape)
        )
        Spacer(Modifier.height(5.dp))
        Text(text = name , fontSize = 15.sp , color = Color.Blue)
    }

}