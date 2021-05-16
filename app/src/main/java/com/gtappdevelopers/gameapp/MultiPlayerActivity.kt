package com.gtappdevelopers.gameapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
var Online = true;

class MultiPlayerActivity : AppCompatActivity() {
    lateinit var onlineBtn : Button
    lateinit var offlineBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player)
        onlineBtn = findViewById(R.id.idBtnOnline)
        offlineBtn = findViewById(R.id.idBtnOffline)
        onlineBtn.setOnClickListener {
            startActivity(Intent(this,OnlinePlayerActivity::class.java))
            Online = true
            singleUser = true;
        }

        offlineBtn.setOnClickListener {
            startActivity(Intent(this , SinglePlayerActivity::class.java))
            singleUser = false;
            Online = false

        }

    }
}