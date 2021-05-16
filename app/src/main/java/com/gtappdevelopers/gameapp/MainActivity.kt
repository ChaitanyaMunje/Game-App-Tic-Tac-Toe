package com.gtappdevelopers.gameapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

var singleUser = false

class MainActivity : AppCompatActivity() {

    lateinit var singlePlayerBtn: Button
    lateinit var multiPlayerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlePlayerBtn = findViewById(R.id.idBtnSinglePlayer)
        multiPlayerBtn = findViewById(R.id.idBtnMultiPlayer)
        singlePlayerBtn.setOnClickListener {
            singleUser = true
            startActivity(Intent(this, SinglePlayerActivity::class.java))
        }

        multiPlayerBtn.setOnClickListener {
            singleUser = false
            startActivity(Intent(this, MultiPlayerActivity::class.java))
        }
    }
}