package ru.geekbrains.noteapp.viewmodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.noteapp.viewstate.CustomViewState

open class CustomViewModel<T, VS : CustomViewState<T>> : ViewModel() {

    protected val viewStateLiveData: MutableLiveData<VS> = MutableLiveData()

    fun viewState(): LiveData<VS> = viewStateLiveData

}