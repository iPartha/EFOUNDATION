package com.ipartha.efoundation.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.ResponseBody

suspend fun  resultLiveData(networkCall: suspend () -> Result<ResponseBody>,
                          saveImageCall: suspend (Bitmap) -> Unit,
                          getImageCall: suspend () -> Bitmap?): Result<Bitmap> {

    val response = networkCall.invoke()
    return  if (response.status == Result.Status.SUCCESS && response.data != null) {
        val inputStream = response.data.byteStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        saveImageCall.invoke(bitmap)
        (Result.success(bitmap))
    } else {
        val cachedBitmap = getImageCall.invoke()
        if (cachedBitmap != null) {
            Result.error(response.message!!, cachedBitmap)
        } else {
            Result.error(response.message!!)
        }
    }
}

suspend fun  resultLiveData(getImageCall: suspend () -> Bitmap?): Result<Bitmap> {
    val cachedBitmap = getImageCall.invoke()
    return if (cachedBitmap == null) {
        (Result.error("No cached image", cachedBitmap))
    } else {
        Result.success(cachedBitmap)
    }
}

