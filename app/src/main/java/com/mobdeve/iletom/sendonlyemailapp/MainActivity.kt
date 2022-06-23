package com.mobdeve.iletom.sendonlyemailapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        load email draft
        val sharedPreference =  getSharedPreferences("EmailDraft",Context.MODE_PRIVATE)
        val receiver = sharedPreference.getString("receiver","")
        val subject = sharedPreference.getString("subject","")
        val body = sharedPreference.getString("body","")

        binding.tvDraftReceiver.text = receiver
        binding.tvDraftSubject.text = subject
        binding.tvDraftBody.text = body

//      send email click function
        binding.btnNew.setOnClickListener { view: View? ->
            var goToSendEmail = Intent(this, SendEmailActivity::class.java)

            startActivity(goToSendEmail)
            finish()
        }

//        view draft function
        binding.clDraft.setOnClickListener{ view: View? ->
            var goToSendEmail = Intent(this, SendEmailActivity::class.java)

            startActivity(goToSendEmail)
            finish()
        }
    }


}