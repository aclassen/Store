package org.burnoutcrew.storekmp.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual abstract class CommonViewModel : ViewModel() {
    internal actual val viewModelScope: CoroutineScope
        get() = (this as ViewModel).viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}