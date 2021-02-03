package ru.geekbrains.noteapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ru.geekbrains.noteapp.R
import ru.geekbrains.noteapp.viewmodel.listener.OpenFragmentListener
import ru.geekbrains.noteapp.viewmodel.viewmodel.BaseViewModel
import ru.geekbrains.noteapp.viewstate.BaseViewState


abstract class CustomFragment <T, VS : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int

    abstract protected var _ui: ViewBinding
    val ui get() = _ui!!

    protected var openFragmentListener: OpenFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenFragmentListener) {
            openFragmentListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.viewState().observe(this, object : Observer<VS> {
            override fun onChanged(state: VS?) {
                if (state == null) return
                if (state.data != null) onDataExist(state.data)
                if (state.error != null) onError(state.error)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = ui.root  //inflater.inflate(layoutRes, container, false)
        bindView(view)
        return view
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _ui = null
//    }

    abstract fun bindView(view: View)

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