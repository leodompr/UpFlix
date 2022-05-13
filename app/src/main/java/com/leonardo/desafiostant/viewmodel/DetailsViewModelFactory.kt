package com.leonardo.desafiostant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardo.desafiostant.repositories.DetailsRepository

class DetailsViewModelFactory (private val repository: DetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(this.repository) as T
        } else {
            throw  IllegalArgumentException("ViewModel Not Found")
        }
    }
}