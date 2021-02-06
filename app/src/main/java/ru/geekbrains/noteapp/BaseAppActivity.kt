package ru.geekbrains.noteapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import ru.geekbrains.noteapp.LoggerMode.DEBUG
import ru.geekbrains.noteapp.fragment.NoteListFragment
import ru.geekbrains.noteapp.viewmodel.listener.OpenFragmentListener


class BaseAppActivity : AppCompatActivity(), OpenFragmentListener,
    NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_app)
        setSupportActionBar(findViewById(R.id.toolbar))

        bindView()
        addFragment(NoteListFragment.newInstance())
    }

    private fun bindView() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view_main)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        return true
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    override fun addFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(id, fragment)
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun popBackStack() = onBackPressed()

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout_activity_baseapp)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount == 0) {
                super.onBackPressed()
            } else {
                supportFragmentManager.popBackStack()
            }
            if (DEBUG) {
                Log.d(
                    "BaseAppActivity",
                    "onBackPressed -> remained in stack: " + supportFragmentManager.backStackEntryCount
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                createAlertDialog(R.string.action_settings)
                true
            }
            R.id.action_search -> {
                createAlertDialog(R.string.action_search)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notes -> createAlertDialog(R.string.menu_item_notes)
            R.id.nav_daily_notes -> createAlertDialog(R.string.menu_item_daily_notes)
            R.id.nav_archive -> createAlertDialog(R.string.menu_item_archive)
            R.id.nav_settings -> createAlertDialog(R.string.action_settings)
            R.id.nav_about -> createAlertDialog(R.string.menu_item_about)
            else -> createAlertDialog(R.string.error_wrong_menu_item)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout_activity_baseapp)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createAlertDialog(stringId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(stringId)
            .setMessage(stringId)
//                .setIcon(drawableId)
            .setCancelable(true)
            .setPositiveButton(R.string.button_ok, { dialog, id -> })
        val alert = builder.create()
        alert.show()
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, BaseAppActivity::class.java)
    }
}
