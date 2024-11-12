package com.project.jufood.domain.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

fun convertByteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return bitmap.asImageBitmap()
}

fun convertImageBitmapToByteArray(bitmap: ImageBitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}