package com.ipartha.efoundation.mvvm

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.channels.Channel
import androidx.lifecycle.liveData
import com.ipartha.efoundation.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


class RandomPictureViewModel(private val repository: RandomPictureRepository) : ViewModel() {

    private val commandsChannel = Channel<Size?>()

    val randomPicture : LiveData<Result<Bitmap>> = liveData(Dispatchers.IO) {
        commandsChannel.consumeEach {
            emit(Result.loading())
            if (it != null) {
                emit(repository.getRandomPicture(it.width, it.height))
            } else {
                emit(repository.getRandomPicture())
            }
        }
    }

    fun getRandomPicture(width: Int, height: Int) {
        GlobalScope.launch {
            commandsChannel.send(Size(width, height))
        }
    }

    fun getRandomPicture() {
        GlobalScope.launch {
            commandsChannel.send(null)
        }
    }

}

class RandomPictureViewModelFactory(private val repository: RandomPictureRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomPictureViewModel(repository) as T
    }
}

data class Size(val width: Int, val height: Int)