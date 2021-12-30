package com.ipartha.efoundation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.ipartha.efoundation.data.Result
import com.ipartha.efoundation.databinding.ActivityMainBinding
import com.ipartha.efoundation.mvvm.RandomPictureRepository
import com.ipartha.efoundation.mvvm.RandomPictureViewModel
import com.ipartha.efoundation.mvvm.RandomPictureViewModelFactory
import com.ipartha.efoundation.ui.disable
import com.ipartha.efoundation.ui.enable
import com.ipartha.efoundation.ui.hide
import com.ipartha.efoundation.ui.show

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: RandomPictureViewModel
    private val binding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(binding)
    }

    private fun initView(binding : ActivityMainBinding) {

        val consumerMenuRepository = RandomPictureRepository(this)
        viewModel = ViewModelProviders.of(this,
            RandomPictureViewModelFactory(consumerMenuRepository)
        ).get(RandomPictureViewModel::class.java)

        observeRandomPicture(binding)
        viewModel.getRandomPicture()
        binding.randomPictureClick = randomPictureClick

    }

    private fun observeRandomPicture(binding : ActivityMainBinding) {
        viewModel.randomPicture.observe(this, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.getRandomPic.enable()
                    result.data?.let {
                        binding.image.setImageBitmap(it)
                    }
                }
               Result.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    binding.getRandomPic.enable()
                    result.data?.let {
                        binding.image.setImageBitmap(it)
                    }
                    Toast.makeText(this.applicationContext, result.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private val randomPictureClick = View.OnClickListener {
        binding.getRandomPic.disable()
        viewModel.getRandomPicture(1000, 1000)
    }

}
