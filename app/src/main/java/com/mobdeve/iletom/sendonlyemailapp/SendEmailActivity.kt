package com.mobdeve.iletom.sendonlyemailapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivitySendEmailBinding

class SendEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        set discard function
        binding.btnDiscard.setOnClickListener { view: View? ->
            var goToMainActivity = Intent(this, MainActivity::class.java)

            startActivity(goToMainActivity)
            finish()
        }


        //    send email function
//        binding.btnSendEmail.setOnClickListener{ view: View? ->
//            var receiver: String = binding.etReceiver.getText().toString()
//            var subject: String = binding.etSubject.getText().toString()
//            var body: String = binding.etBody.getText().toString()
//
////            only proceeds if inputs are not empty
//            if (receiver.length >= 1 && subject.length >= 1 && body.length >= 1) {
//
//            }
//
//        }
    }

    override fun onBackPressed() {
        var receiver: String = binding.etReceiver.getText().toString()
        var subject: String = binding.etSubject.getText().toString()
        var body: String = binding.etBody.getText().toString()

        //        set shared preference
        var sharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)

//            only proceeds if inputs are not empty
        if (receiver.length >= 1 && subject.length >= 1 && body.length >= 1) {
//            save to sharedPreference
            var editor: SharedPreferences.Editor = sharedPreference.edit()

            editor.putString("receiver", receiver)
            editor.putString("subject", subject)
            editor.putString("body", body)
            editor.commit()

//            return to home screen
            var goToMainActivity = Intent(this, MainActivity::class.java)

            startActivity(goToMainActivity)
            finish()
        } else {
//            show toast saying that inputs are empty
            Toast.makeText(applicationContext, "Please make sure all entries have text.", Toast.LENGTH_LONG).show()
        }
    }



}