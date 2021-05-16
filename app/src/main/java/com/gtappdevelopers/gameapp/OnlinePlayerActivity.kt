package com.gtappdevelopers.gameapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var isCodeMaker = true;
var code = "null";
var codeFound = false
var checkTemp = true
var keyValue:String = "null"
class OnlinePlayerActivity : AppCompatActivity() {

    lateinit var codeEdt: EditText
    lateinit var createBtn : Button
    lateinit var joinBtn : Button
    lateinit var headTV : TextView
    lateinit var loadingPB : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_player)
        codeEdt = findViewById(R.id.idEdtGameCode)
        createBtn = findViewById(R.id.Create)
        joinBtn = findViewById(R.id.Join)
        headTV = findViewById(R.id.idTVhead)
        loadingPB = findViewById(R.id.progressBar)

        joinBtn.setOnClickListener {
            code = "null";
            codeFound = false
            checkTemp = true
            keyValue= "null"
            code = codeEdt.text.toString()
            if(code != "null" && code != null && code != "") {
                createBtn.visibility = View.GONE
                joinBtn.visibility = View.GONE
                codeEdt.visibility = View.GONE
                headTV.visibility = View.GONE
                loadingPB.visibility = View.VISIBLE
                isCodeMaker = false;
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object:
                    ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data:Boolean = isValueAvailable(snapshot , code)

                        Handler().postDelayed({
                            if(data == true) {
                                codeFound = true
                                accepted()
                                createBtn.visibility = View.VISIBLE
                                joinBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE
                            }
                            else{
                                createBtn.visibility = View.VISIBLE
                                joinBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE
                                errorMsg("Invalid Code")
                            }
                        } , 2000)


                    }


                })

            }
            else
            {
                errorMsg("Enter Code Properly")
            }
        }


        createBtn.setOnClickListener {

            code = "null";
            codeFound = false
            checkTemp = true
            keyValue= "null"
            code = codeEdt.text.toString()
            createBtn.visibility = View.GONE
            joinBtn.visibility = View.GONE
            codeEdt.visibility = View.GONE
            headTV.visibility = View.GONE
            loadingPB.visibility = View.VISIBLE
            if(code != "null" && code != null && code != "") {

                isCodeMaker = true;
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object  :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check = isValueAvailable(snapshot , code)

                        Handler().postDelayed({
                            if(check == true) {
                                createBtn.visibility = View.VISIBLE
                                joinBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE

                            }
                            else{
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
                                isValueAvailable(snapshot,code)
                                checkTemp = false
                                Handler().postDelayed({
                                    accepted()
                                    errorMsg("Please don't go back")
                                } , 300)

                            }
                        }, 2000)
                    }

                })
            }
            else
            {
                createBtn.visibility = View.VISIBLE
                joinBtn.visibility = View.VISIBLE
                codeEdt.visibility = View.VISIBLE
                headTV.visibility = View.VISIBLE
                loadingPB.visibility = View.GONE
                errorMsg("Enter Code Properly")
            }

        }
    }

    fun accepted() {
        startActivity(Intent(this, OnlineMultiPlayerGame::class.java));
        createBtn.visibility = View.VISIBLE
        joinBtn.visibility = View.VISIBLE
        codeEdt.visibility = View.VISIBLE
        headTV.visibility = View.VISIBLE
        loadingPB.visibility = View.GONE

    }

    fun errorMsg(value : String){
        Toast.makeText(this , value  , Toast.LENGTH_SHORT).show()
    }

    fun isValueAvailable(snapshot: DataSnapshot , code : String): Boolean {
        var data = snapshot.children
        data.forEach{
            var value = it.getValue().toString()
            if(value == code)
            {
                keyValue = it.key.toString()
                return true;
            }
        }
        return false
    }

}