package com.ipartha.efoundation.networking

class PicsumRemoteSource(private val picsumAPI: PicsumAPI) : BaseDataSource() {

    suspend fun getRandomPicture(width: Int, height: Int) = getResult { picsumAPI.getPicsumPhoto(width, height) }

}