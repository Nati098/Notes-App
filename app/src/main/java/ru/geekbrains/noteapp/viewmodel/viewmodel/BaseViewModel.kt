package ru.geekbrains.noteapp.viewmodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.noteapp.viewstate.BaseViewState

open class BaseViewModel<T, VS : BaseViewState<T>> : ViewModel() {

    protected val viewStateLiveData: MutableLiveData<VS> = MutableLiveData()

    fun viewState(): LiveData<VS> = viewStateLiveData

}