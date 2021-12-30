package com.ipartha.efoundation.mvvm

import android.content.Context
import com.ipartha.efoundation.data.resultLiveData
import com.ipartha.efoundation.networking.PicsumAPI
import com.ipartha.efoundation.networking.PicsumRemoteSource
import com.ipartha.efoundation.networking.RetrofitService
import com.ipartha.efoundation.storage.ImageDB


class RandomPictureRepository(applicationContext : Context) {

    private val imageDB = ImageDB(applicationContext)

    private val picsumAPI : PicsumAPI = RetrofitService.createService(PicsumAPI::class.java)
    private val remoteSource = PicsumRemoteSource(picsumAPI)

    suspend fun getRandomPicture(width: Int, height: Int) = resultLiveData(
        networkCall = { remoteSource.getRandomPicture(width,height) },
        saveImageCall = { imageDB.saveImage(it) },
        getImageCall = {imageDB.loadImage()}
    )

    suspend fun getRandomPicture() = resultLiveData (
        getImageCall = {imageDB.loadImage()}
    )
}