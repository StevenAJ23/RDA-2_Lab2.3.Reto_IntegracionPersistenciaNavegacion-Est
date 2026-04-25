package com.example.myapplication

import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataStore: ReviewDataStore
) : ViewModel() {

    var rating = mutableFloatStateOf(3f)
        private set

    private val _comment = MutableStateFlow(
        savedStateHandle["comment"] ?: ""
    )
    val comment: StateFlow<String> = _comment

    init {
        viewModelScope.launch {
            dataStore.draftContent.collect { draft ->
                if (_comment.value.isEmpty()) {
                    updateComment(draft)
                }
            }
        }
    }

    fun updateRating(value: Float) {
        rating.floatValue = value
    }

    fun updateComment(value: String) {
        _comment.value = value
        savedStateHandle["comment"] = value
    }

    fun saveDraft() {
        viewModelScope.launch {
            dataStore.saveDraft(_comment.value)
        }
    }
}