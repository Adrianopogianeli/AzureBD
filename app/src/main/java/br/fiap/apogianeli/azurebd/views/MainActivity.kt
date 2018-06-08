package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.business.PriorityBusiness
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.repository.PriorityCacheConstants
import br.fiap.apogianeli.azurebd.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mapsActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Instancia variaveis

        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)
        //mapsActivity = MapsActivity(this)

        loadPriorityCache()
        startDefaultFragment()
        formatUserName()
        formatDate()

    }

    override fun onStart() {
        super.onStart()
        startDefaultFragment()
    }

    override fun onResume() {
        super.onResume()
        startDefaultFragment()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        val id = item.itemId
        var fragment: android.support.v4.app.Fragment? = null

        when (id) {
            R.id.nav_done -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            R.id.nav_todo -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            R.id.nav_mapas ->  {
                //fragment =  map()
                mapas()
                //Toast.makeText(this,"Ok abra o mapas",Toast.LENGTH_LONG).show()
                return false
            }
            R.id.nav_logout -> {
                handleLogout()
                return false
            }
            R.id.nav_about ->{
                sobre()
                return false
            }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startDefaultFragment() {
        val fragment: android.support.v4.app.Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun loadPriorityCache(){
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun handleLogout() {

        // Apagar os dados do usuario
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun mapas(){
        try {
            startActivity(Intent(this,MapsActivity::class.java))

            finish()
        }catch (e: Exception){
            Toast.makeText(this,getString(R.string.erro_cant_open_activity),Toast.LENGTH_LONG).show()
            println("----------------------->${e.message}")
        }
    }

    private fun sobre(){
        try{
            startActivity(Intent(this,AboutActivity::class.java))
            finish()
        }catch (e: Exception){
            Toast.makeText(this,getString(R.string.erro_cant_open_activity),Toast.LENGTH_LONG).show()
            println("----------------------->${e.message}")
        }
    }

    private fun formatUserName(){
        val str = "Óla, ${mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)}"
        textHello.text = str
        // change nav_header
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)
        // change nav_header elements
        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)
        name.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        email.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
    }

    private fun formatDate(){
        val c = Calendar.getInstance()

        val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novemembro", "Dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        textDateDescription.text = str

    }


}