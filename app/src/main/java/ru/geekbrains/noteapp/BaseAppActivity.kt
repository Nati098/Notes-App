package ru.geekbrains.noteapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class BaseAppActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_app)
        setSupportActionBar(findViewById(R.id.toolbar))

        bindView()
    }

    private fun bindView() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view_main)
        navigationView.setNavigationItemSelectedListener(this)

        findViewById<FloatingActionButton>(R.id.fab_base_app).setOnClickListener { view ->
            Snackbar.make(view, "Floating Button was pressed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_search -> true
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

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout_activity_main)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createAlertDialog(stringId : Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(stringId)
                .setMessage(stringId)
//                .setIcon(drawableId)
                .setCancelable(true)
                .setPositiveButton(R.string.button_ok, { dialog, id -> })
        val alert = builder.create()
        alert.show()
    }
}