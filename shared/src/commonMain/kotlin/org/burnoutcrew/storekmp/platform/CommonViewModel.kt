package org.burnoutcrew.storekmp.platform

import kotlinx.coroutines.CoroutineScope

expect abstract class CommonViewModel() {
    internal val viewModelScope: CoroutineScope
    protected fun onCleared()
}