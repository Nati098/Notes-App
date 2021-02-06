package ru.geekbrains.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import ru.geekbrains.noteapp.databinding.ActivitySplashBinding
import ru.geekbrains.noteapp.model.exception.NoAuthException
import ru.geekbrains.noteapp.viewmodel.viewmodel.SplashViewModel
import ru.geekbrains.noteapp.viewstate.SplashViewState


private const val INTENT_SIGN_IN = 11
private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy { ViewModelProvider(this).get(SplashViewModel::class.java) }
    override val layoutRes: Int = R.layout.activity_splash
    override val ui: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    //TODO
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        setContentView(ui.root)
//    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun onDataExist(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    override fun onError(error: Throwable) {
        when (error) {
            is NoAuthException -> startAuth()
            else -> error.message?.let { showMessage(it) }
        }
    }

    private fun startAuth() {
        val builders = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher_note)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(builders)
                .build(),
            INTENT_SIGN_IN)
    }

    private fun startMainActivity() {
        startActivity(BaseAppActivity.getStartIntent(context = this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }
}