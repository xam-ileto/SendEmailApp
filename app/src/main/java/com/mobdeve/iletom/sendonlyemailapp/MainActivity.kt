package com.mobdeve.iletom.sendonlyemailapp

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.iletom.sendonlyemailapp.adapter.EmailAdapter
import com.mobdeve.iletom.sendonlyemailapp.dao.EmailDAO
import com.mobdeve.iletom.sendonlyemailapp.dao.EmailDAOImpl
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivityMainBinding
import com.mobdeve.iletom.sendonlyemailapp.model.Email
import java.util.*

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

        //        if no draft exists, do not render draft template
        if (receiver == "" && subject == "" && body == "")
            binding.clDraft.visibility = View.GONE
        else { // if at least one input exists
            if (receiver == "")
                binding.tvDraftReceiver.text = "<no receiver in draft>"
            else if (receiver != null)
                    binding.tvDraftReceiver.text = prepareTextForDisplay(receiver)

            if (subject == "")
                binding.tvDraftSubject.text = "<no subject in draft>"
            else if (subject != null)
                binding.tvDraftSubject.text = prepareTextForDisplay(subject)

            if (body == "")
                binding.tvDraftBody.text = "<no body in draft>"
            else if (body != null)
                binding.tvDraftBody.text = prepareTextForDisplay(body)
        }


        dao = EmailDAOImpl()
        var allPrefs: HashMap<String, String>  = getSharedPreferences("SentEmails", Context.MODE_PRIVATE).getAll() as HashMap<String, String>
        emailArrayList = dao.getEmails(allPrefs)
        binding.rvEmailList.setLayoutManager(LinearLayoutManager(applicationContext))

        emailAdapter = EmailAdapter(applicationContext, emailArrayList)
        binding.rvEmailList.setAdapter(emailAdapter)

//      send email click function
        binding.btnNew.setOnClickListener { view: View? ->
            if (binding.clDraft.visibility != View.GONE) { //if there is already a draft present
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Draft email present")
                    .setMessage("By creating a new email, you'll automatically delete the draft. Do you wish to proceed?") // The positive button refers to a "yes, I accept deleted the present draft."
                    .setPositiveButton(
                        R.string.yes
                    ) { dialog, which ->
                        var goToSendEmail = Intent(this, SendEmailActivity::class.java)
                        goToSendEmail.putExtra("receiver","")
                        goToSendEmail.putExtra("subject", "")
                        goToSendEmail.putExtra("body", "")

                        var emailSharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)
                        var emailEditor: SharedPreferences.Editor = emailSharedPreference.edit()

                        emailEditor.putString("receiver", "")
                        emailEditor.putString("subject", "")
                        emailEditor.putString("body", "")
                        emailEditor.commit()

                        startActivity(goToSendEmail)
                        finish()
                    }
                    .setNegativeButton(R.string.no, null)
                    .setIcon(R.drawable.ic_dialog_alert)
                    .show()

            } else {
                var goToSendEmail = Intent(this, SendEmailActivity::class.java)
                goToSendEmail.putExtra("receiver","")
                goToSendEmail.putExtra("subject", "")
                goToSendEmail.putExtra("body", "")

                startActivity(goToSendEmail)
                finish()
            }


        }

//        view draft function
        binding.clDraft.setOnClickListener{ view: View? ->
            var goToSendEmail = Intent(this, SendEmailActivity::class.java)
            goToSendEmail.putExtra("receiver",receiver)
            goToSendEmail.putExtra("subject", subject)
            goToSendEmail.putExtra("body", body)

            startActivity(goToSendEmail)
            finish()
        }

//        view latest email function
        binding.btnLatest.setOnClickListener { view: View? ->
            if (emailArrayList.size == 0)
                Toast.makeText(applicationContext, "There are no emails currently.", Toast.LENGTH_LONG).show()
            else {
                var goToLatestEmail = Intent(this, LatestEmailActivity::class.java)

                var latestEmail = this.emailArrayList.get(emailArrayList.size-1)

                goToLatestEmail.putExtra("receiver", latestEmail.receiver)
                goToLatestEmail.putExtra("subject", latestEmail.subject)
                goToLatestEmail.putExtra("body", latestEmail.body)

                startActivity(goToLatestEmail)
            }
        }


    }

    private fun prepareTextForDisplay(x: String): String? {
        return x.replace("(\\n)+".toRegex(), " ")
    }


}