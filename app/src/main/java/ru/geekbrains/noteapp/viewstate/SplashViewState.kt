package ru.geekbrains.noteapp.viewstate


class SplashViewState(val isAuth: Boolean? = null, error: Throwable? = null) : BaseViewState<Boolean?>(isAuth, error)