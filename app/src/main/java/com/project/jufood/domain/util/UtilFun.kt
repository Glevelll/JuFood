package com.project.jufood.domain.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.util.Calendar

// Работа с картинками
fun convertByteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return bitmap.asImageBitmap()
}

fun convertImageBitmapToByteArray(bitmap: ImageBitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun getCurrentDateDisplay(): String {
    val currentDate = Calendar.getInstance()
    return "${currentDate.get(Calendar.DAY_OF_MONTH)} ${getMonthName(currentDate.get(Calendar.MONTH))}"
}

fun getCurrentDateQuery(): String {
    val currentDate = Calendar.getInstance()
    return String.format("%02d.%02d", currentDate.get(Calendar.DAY_OF_MONTH), currentDate.get(
        Calendar.MONTH) + 1)
}

fun getMonthName(month: Int): String {
    return when (month) {
        Calendar.JANUARY -> "января"
        Calendar.FEBRUARY -> "февраля"
        Calendar.MARCH -> "марта"
        Calendar.APRIL -> "апреля"
        Calendar.MAY -> "мая"
        Calendar.JUNE -> "июня"
        Calendar.JULY -> "июля"
        Calendar.AUGUST -> "августа"
        Calendar.SEPTEMBER -> "сентября"
        Calendar.OCTOBER -> "октября"
        Calendar.NOVEMBER -> "ноября"
        Calendar.DECEMBER -> "декабря"
        else -> ""
    }
}
