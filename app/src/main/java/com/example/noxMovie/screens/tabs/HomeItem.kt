package com.example.noxMovie.screens.tabs

import HomeTab
import SearchTab
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun HomeItem() {
    val isDark = isSystemInDarkTheme() // checking if device is in dark mode or no
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = if (isDark) Color.White.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.7f)

    Navigator(HomeTab) { navigator ->
        val currentScreen = navigator.lastItem

        val showBottomBar = currentScreen is HomeTab || currentScreen is SearchTab
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentScreen is HomeTab,
                            onClick = { navigator.replaceAll(HomeTab) },
                            icon = {
                                Icon(
                                    imageVector = if (currentScreen is HomeTab) Icons.Filled.Home else Icons.Outlined.Home,
                                    contentDescription = null,
                                    tint = if (currentScreen is HomeTab) selectedColor else unselectedColor
                                )
                            },
                            label = {
                                Text(
                                    "Home",
                                    color = if (currentScreen is HomeTab) selectedColor else unselectedColor
                                )
                            }
                        )
                        NavigationBarItem(
                            selected = currentScreen is SearchTab,
                            onClick = { navigator.replaceAll(SearchTab) },
                            icon = {
                                Icon(
                                    Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = if (currentScreen is HomeTab) selectedColor else unselectedColor
                                )
                            },
                            label = {
                                Text(
                                    "Search",
                                    color = if (currentScreen is HomeTab) selectedColor else unselectedColor
                                )
                            },
                        )

                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CurrentScreen()
            }
        }

    }
}
