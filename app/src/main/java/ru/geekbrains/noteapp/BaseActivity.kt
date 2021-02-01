package ru.geekbrains.noteapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ru.geekbrains.noteapp.viewmodel.viewmodel.CustomViewModel
import ru.geekbrains.noteapp.viewmodel.viewstate.CustomViewState


abstract class BaseActivity<T, VS : CustomViewState<T>> : AppCompatActivity() {

    abstract val viewModel: CustomViewModel<T, VS>
    abstract val layoutRes: Int
    abstract val ui: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
//        createUi()
        setContentView(ui.root)

        viewModel.viewState().observe(this, object : Observer<VS> {
            override fun onChanged(state: VS?) {
                if (state == null) return
                if (state.data != null) onDataExist(state.data)
                if (state.error != null) onError(state.error)
            }
        })


    }

//    abstract fun createUi()
    abstract fun onDataExist(data: T)

    protected fun onError(error: Throwable) {
        if (error.message != null) showMessage(error.message!!)
    }

    protected fun showMessage(msg: String) {
        val builder = AlertDialog.Builder(ui.root.context)
        builder.setTitle(msg)
            .setCancelable(false)
            .setPositiveButton(R.string.button_ok) { dialog, id -> }
        val alert = builder.create()
        alert.show()
    }
}