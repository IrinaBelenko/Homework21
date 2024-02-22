package com.example.homework21

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState
    private val repo = MyApplication.getApp().repo
    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
         var result = ""
         val heroes = async { repo.getHeroes2() } .await()
        withContext(Dispatchers.Main){
            _uiState.postValue(UIState.Result("Test"))
        }
        }
    }

    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val responseHeroes: String) : UIState()
        class Error(val description: String) : UIState()
    }

}
