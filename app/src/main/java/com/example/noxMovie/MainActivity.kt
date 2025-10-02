package com.example.noxMovie

import TabsScreen
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.noxMovie.network.isNetworkAvailable
import com.example.noxMovie.ui.theme.SelectTheme
import com.orhanobut.hawk.Hawk

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Hawk.init(this).build()
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "اینترنت موجود نیست", Toast.LENGTH_LONG).show()
        }
        setContent {
            SelectTheme {
                Navigator(TabsScreen()){
                    CurrentScreen()
                }
            }
        }
    }

}

