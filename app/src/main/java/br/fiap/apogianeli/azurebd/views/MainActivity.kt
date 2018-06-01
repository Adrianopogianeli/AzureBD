package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Instancia variaveis
        mSecurityPreferences = SecurityPreferences(this)

        startDefaultFragment()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

/* Menu settings
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_done -> {
                // Handle the camera action
                fragment = TaskListFragment.newInstance()
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance()
            }
            R.id.nav_logout -> {
                handleLogout()
            }
            R.id.nav_mapas -> {

            }
            R.id.nav_about -> {

            }

        }

        // chamar o frament
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.frameContent,fragment).commit()

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun startDefaultFragment(){
        var fragment: Fragment = TaskListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frameContent,fragment).commit()

    }

    private fun handleLogout(){

        // apagar o shared preferences do user
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)

        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}
