package ru.geekbrains.noteapp.viewmodel.listener

import androidx.fragment.app.Fragment

interface OpenFragmentListener {

    fun addFragment(fragment: Fragment)
    fun addFragment(id: Int, fragment: Fragment)

    fun replaceFragment(fragment: Fragment)

}