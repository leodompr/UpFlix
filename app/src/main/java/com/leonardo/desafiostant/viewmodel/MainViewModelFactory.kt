package com.leonardo.desafiostant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardo.desafiostant.repositories.MainRepository

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MianViewModel::class.java)){
            MianViewModel(this.repository) as T
        } else{
            throw  IllegalArgumentException("ViewModel Not Found")
        }
    }


}