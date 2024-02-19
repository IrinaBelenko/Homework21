package com.example.homework21

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState
    private val repo = MyApplication.getApp().repo
    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getHeroes2()
                if (response != null) {
                    withContext(Dispatchers.Main) {
//                        _uiState.postValue(
//                            UIState.Result(response.images.lg, response?.name)
//                        )
                    }
                } else
                    _uiState.postValue(UIState.Error("Error"))
            }
        }
    }

    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val imageURL: String, val nameHero: String, val listValue: String) : UIState()
        class Error(val description: String) : UIState()
    }

}
