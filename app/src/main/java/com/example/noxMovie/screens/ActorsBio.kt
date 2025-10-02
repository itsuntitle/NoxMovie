package com.example.noxMovie.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage

data class ActorsBio(val name: String , val img: String): Screen{
    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp) , horizontalAlignment = Alignment.End) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = img,
                    contentDescription = "Actors bio",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("$name", modifier = Modifier.padding(top = 15.dp))

            }
            Spacer(modifier = Modifier.height(2.dp))
            HorizontalDivider(thickness = 2.dp , color = Color.Gray)
            val bioName = when(name){
                "Anne Hathway" -> "نام کامل : آن هاث وی"
                "Cristian Bale" -> "نام کامل : کیریستن بیل"
                "Robert Downey Jr" -> "نام کامل : رابرات داونی جونیور"
                "Chris Evans" -> "نام کامل : کیریس اوانس"
                "Cillian Murphy" -> "نام کامل : کیلیان مورفی"
                "Tom Hardy" -> "نام کامل: تام هاردی"

                else -> {"نام تعریف نشده"}
            }
            val bioAge = when(name){
                "Anne Hathway" -> "سن : 42"
                "Cristian Bale" -> "سن : 51"
                "Robert Downey Jr" -> "سن : 60"
                "Chris Evans" -> "سن : 44"
                "Cillian Murphy" -> "سن : 49"
                "Tom Hardy" -> "سن : 48"

                else -> {"نام تعریف نشده"}
            }
            val bioText = when (name) {
                "Anne Hathway" -> "آن هاثوی، بازیگر آمریکایی متولد 1982 است. او کارش را با فیلم The Princess Diaries آغاز کرد و با فیلم‌هایی مثل The Devil Wears Prada، Les Misérables (که برای آن جایزه اسکار بهترین بازیگر نقش مکمل زن را گرفت) و Interstellar به شهرت جهانی رسید. آن هاثوی به خاطر توانایی بازی در نقش‌های متنوع، از کمدی تا درام، شناخته می‌شود."

                "Cristian Bale" -> "کریستین بیل، بازیگر انگلیسی متولد 1974 است. او به خاطر ایفای نقش‌های سخت و تغییرات شدید فیزیکی برای هر نقش معروف است. از آثار برجسته‌اش می‌توان به سه‌گانه‌ی Batman: Batman Begins، The Dark Knight و The Dark Knight Rises، American Psycho، The Fighter (که برای آن جایزه اسکار گرفت) و Vice اشاره کرد."

                "Robert Downey Jr" -> "رابرت داونی جونیور، بازیگر آمریکایی متولد 1965 است. او کارش را از کودکی آغاز کرد و بعد از مواجهه با مشکلات شخصی، دوباره به شهرت رسید. او بیشتر با نقش تونی استارک / آیرون‌من در دنیای سینمایی مارول شناخته می‌شود و در فیلم‌هایی مثل Sherlock Holmes، Chaplin و Tropic Thunder نیز بازی کرده است."

                "Chris Evans" -> "کریس اوانز، بازیگر آمریکایی متولد 1981 است. او در ابتدا با فیلم‌های طنز و اکشن شناخته شد، اما شهرت اصلی خود را با نقش کاپیتان آمریکا در دنیای سینمایی مارول (Captain America: The First Avenger، Avengers: Endgame و ... ) به دست آورد. او همچنین در فیلم‌هایی مثل Snowpiercer و Knives Out نقش‌آفرینی کرده است."

                "Cillian Murphy" -> "کیلیان مورفی، بازیگر ایرلندی متولد 1976 است. او با بازی در سریال Peaky Blinders به شهرت رسید و در فیلم‌هایی مثل 28 Days Later، Inception، Dunkirk و Oppenheimer نقش‌های به‌یادماندنی داشته است. سبک بازی او معمولاً آرام و عمیق است و در نقش‌های پیچیده و روانشناسانه عالی عمل می‌کند."

                "Tom Hardy" -> "تام هاردی، بازیگر انگلیسی متولد 1977 است. او با نقش‌های قدرتمند و متنوعش شناخته می‌شود. از آثار مهم او می‌توان به The Dark Knight Rises (Bane)، Mad Max: Fury Road، Inception، Venom، و Locke اشاره کرد. هاردی به خاطر توانایی بالای بدنی و تغییرات فیزیکی برای نقش‌ها معروف است."

                else -> "بیوگرافی موجود نیست"
            }

            Spacer(Modifier.height(5.dp))
            Text(text = bioName , fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            Text(text = bioAge , fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { //changing direction to rtl
                Text(text = bioText, fontSize = 16.sp , lineHeight = 24.sp )            }


        }

    }

}