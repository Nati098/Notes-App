package ru.geekbrains.noteapp.viewmodel.viewmodel

import ru.geekbrains.noteapp.model.Repository
import ru.geekbrains.noteapp.model.exception.NoAuthException
import ru.geekbrains.noteapp.viewstate.SplashViewState

class SplashViewModel(private val repository: Repository = Repository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(isAuth = true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}