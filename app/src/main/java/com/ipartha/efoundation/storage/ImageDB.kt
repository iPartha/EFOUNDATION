package com.ipartha.efoundation.storage

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*

class ImageDB(private val context : Context) {
    companion object {
        const val FILE_NAME = "test.jpg"
        const val DIRECTORY_NAME = "imageDir"
    }

    suspend fun saveImage(bitmap: Bitmap) {
        val cw = ContextWrapper(context.applicationContext)
        val directory = cw.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
        val file = File(directory, FILE_NAME)
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    suspend fun loadImage() : Bitmap? {
        try {
            val cw = ContextWrapper(context.applicationContext)
            val directory = cw.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
            val file = File(directory, FILE_NAME)
            return BitmapFactory.decodeStream(FileInputStream(file))
        }catch (e : FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }
}