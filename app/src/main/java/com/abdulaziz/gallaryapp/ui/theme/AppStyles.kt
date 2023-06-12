package com.abdulaziz.gallaryapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppStyles {
    val textTitle = TextStyle(color = Green700, fontSize = 24.sp, fontWeight = FontWeight(700))
    val textSubtitle = TextStyle(color = Green500, fontSize = 20.sp, fontWeight = FontWeight(500))
    val textBody = TextStyle(color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight(300))
    val textBodySemiBold = TextStyle(color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight(500))
    val textHint = TextStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight(300))
    val textLabel = TextStyle(color = Color.DarkGray, fontSize = 10.sp, fontWeight = FontWeight(300))
    val textCaption = TextStyle(color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight(300))
}