package ru.geekbrains.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.noteapp.BaseViewState
import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.data.Note

class BaseViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<BaseViewState> = MutableLiveData()


    init {
        viewStateLiveData.value = BaseViewState(Repository.notes)
    }

    fun viewState() : LiveData<BaseViewState> = viewStateLiveData

    fun changeState(notes : List<Note>) {
        viewStateLiveData.value = BaseViewState(notes)
    }
}