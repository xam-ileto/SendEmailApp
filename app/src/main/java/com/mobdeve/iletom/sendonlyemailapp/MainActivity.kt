package com.mobdeve.iletom.sendonlyemailapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.iletom.sendonlyemailapp.adapter.EmailAdapter
import com.mobdeve.iletom.sendonlyemailapp.dao.EmailDAO
import com.mobdeve.iletom.sendonlyemailapp.dao.EmailDAOImpl
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivityMainBinding
import com.mobdeve.iletom.sendonlyemailapp.model.Email

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var emailAdapter: EmailAdapter
    private lateinit var emailArrayList: ArrayList<Email>
    private lateinit var dao: EmailDAO

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

        dao = EmailDAOImpl()
        var sharedPreferenceSentEmails = getSharedPreferences("SentEmails", Context.MODE_PRIVATE)
        var allPrefs: HashMap<String, String>  = getSharedPreferences("SentEmails", Context.MODE_PRIVATE).getAll() as HashMap<String, String>
        emailArrayList = dao.getEmails(allPrefs)
        binding.rvEmailList.setLayoutManager(LinearLayoutManager(applicationContext))

        emailAdapter = EmailAdapter(applicationContext, emailArrayList)
        binding.rvEmailList.setAdapter(emailAdapter)

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