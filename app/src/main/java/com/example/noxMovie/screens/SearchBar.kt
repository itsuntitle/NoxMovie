package com.example.noxMovie.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orhanobut.hawk.Hawk

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextValueChange: (String) -> Unit,
    onSavedItemClicked: (String) -> Unit,
    savedWords: List<String>
) {
    var isFocused by remember { mutableStateOf(false) }
    var savedWordsState by remember { mutableStateOf(savedWords) }
    val isDark = isSystemInDarkTheme()
    val selectedColor = MaterialTheme.colorScheme.primary

// رنگ متن و پس‌زمینه بر اساس دارک یا لایت مود
    val textColor = if (isDark) Color.White else Color.Gray
    val containerColor = if (isDark) Color.DarkGray.copy(alpha = 0.1f) else Color.LightGray.copy(alpha = 0.1f)
    Column(modifier = Modifier.padding(5.dp)) {
        OutlinedTextField(
            value = text,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = textColor,
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = selectedColor
            ),

            onValueChange = onTextValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            label = { Text("Search") },
            keyboardActions = KeyboardActions(
                onSearch = {
                    val historySearch = savedWordsState.toMutableList()
                    if (text.isNotBlank() && !historySearch.contains(text)) {
                        historySearch.add(text)
                    }
                    Hawk.put("savedwords", historySearch)
                    savedWordsState = historySearch  // ← بروزرسانی state مهمه
                    onSavedItemClicked(text)
                }
            ),


            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
        )

        AnimatedVisibility(
            visible = isFocused
        ) {
            ElevatedCard {
                Column {
                    savedWordsState.forEach {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable { onSavedItemClicked(it) }
                        )
                    }
                }
            }
        }


    }
}