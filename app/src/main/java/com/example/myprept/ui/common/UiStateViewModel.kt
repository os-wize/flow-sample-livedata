package com.example.myprept.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myprept.utils.UiStateManager

/**
 * Interface to be implemented by ViewModel class if it makes network requests or updates UI
 */
interface UiStateViewModel {

    /**
     * LiveData for [UiStateManager.UiState]
     */
    val uiState: LiveData<UiStateManager.UiState>
}