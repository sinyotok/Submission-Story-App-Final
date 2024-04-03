package com.aryanto.storyappfinal.utils

sealed class ClientState<T>(
    val data: T? = null,
    val message: String? = null
){
    class SUCCESS<T>(data: T): ClientState<T>(data)
    class LOADING<T>: ClientState<T>()
    class ERROR<T>(message: String, data: T? = null): ClientState<T>(data, message)
}

