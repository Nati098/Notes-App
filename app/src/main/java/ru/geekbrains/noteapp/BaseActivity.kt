package ru.geekbrains.noteapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ru.geekbrains.noteapp.viewmodel.viewmodel.BaseViewModel
import ru.geekbrains.noteapp.viewstate.BaseViewState


abstract class BaseActivity<T, VS : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int
    abstract val ui: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(ui.root)

        viewModel.viewState().observe(this, object : Observer<VS> {
            override fun onChanged(state: VS?) {
                if (state == null) return
                if (state.data != null) onDataExist(state.data)
                if (state.error != null) onError(state.error)
            }
        })
    }

    abstract fun onDataExist(data: T)

    protected fun onError(error: Throwable) {
        if (error.message != null) showMessage(error.message!!)
    }

    protected fun showMessage(msg: String) = AlertDialog.Builder(ui.root.context).setTitle(msg)
        .setCancelable(false)
        .setPositiveButton(R.string.button_ok) { dialog, id -> }.create().show()
}