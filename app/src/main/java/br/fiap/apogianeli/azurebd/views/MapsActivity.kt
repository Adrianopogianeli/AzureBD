package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.util.MapLoad
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        setListeners()
        Map()

    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonMenu -> {
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun setListeners(){
        buttonMenu.setOnClickListener(this)
    }

    private fun Map(){
        val mapFra = MapLoad()
        supportFragmentManager.beginTransaction().replace(R.id.mapFragment, mapFra).commit()
    }

}
