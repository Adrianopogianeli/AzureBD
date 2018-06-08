package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.fiap.apogianeli.azurebd.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        setListeners()

    }

    private fun setListeners() {
        buttonMenuAbout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonMenuAbout -> {
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }



}
