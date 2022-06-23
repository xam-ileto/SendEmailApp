package com.mobdeve.iletom.sendonlyemailapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivityEmailBinding


class EmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailBinding

    // Views needed
    private lateinit var senderTv: TextView  // Views needed
    private lateinit var receiverTv: TextView // Views needed
    private lateinit var subjectTv: TextView  // Views needed
    private lateinit var bodyTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialization of the views
        senderTv = findViewById(R.id.emailSenderTv)
        receiverTv = findViewById(R.id.emailReceiverTv)
        subjectTv = findViewById(R.id.emailSubjectTv)
        bodyTv = findViewById(R.id.emailBodyTv)

        // Get intent data
//        TODO initialize variables
//        val i = intent
//        val sender = "From: me"
//        var receiver: String
//        var subject: String
//        var body: String
//
//        // Set Text attributes of all views
//        senderTv.setText(sender)
//        receiverTv.setText(receiver)
//        subjectTv.setText(subject)
//        bodyTv.setText(body)
    }
}