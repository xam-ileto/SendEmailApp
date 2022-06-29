package com.mobdeve.iletom.sendonlyemailapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivitySendEmailBinding
import com.mobdeve.iletom.sendonlyemailapp.model.Email
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SendEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendEmailBinding
    private var clickedSend = false
    private var discard = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        load up previous draft
        var draftSharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)

        Log.v("receiver", draftSharedPreference.getString("receiver","").toString())
        Log.v("subject", draftSharedPreference.getString("subject","").toString())
        Log.v("body", draftSharedPreference.getString("body","").toString())


        var bundle = intent.extras
        binding.etReceiver.setText("${bundle!!.getString("receiver")}")
        binding.etSubject.setText("${bundle!!.getString("subject")}")
        binding.etBody.setText("${bundle!!.getString("body")}")


//        set discard function
        binding.btnDiscard.setOnClickListener { view: View? ->
            this.discard = true
            removeDraft()

            var goToMainActivity = Intent(this, MainActivity::class.java)

            startActivity(goToMainActivity)
            finish()
        }


        //    send email function
        binding.btnSendEmail.setOnClickListener{ view: View? ->
            var receiver: String = binding.etReceiver.getText().toString()
            var subject: String = binding.etSubject.getText().toString()
            var body: String = binding.etBody.getText().toString()

//            only proceeds if inputs are not empty
            if (receiver.length >= 1 && subject.length >= 1 && body.length >= 1) {
                this.clickedSend = true
                var sharedPreference = getSharedPreferences("SentEmails", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = sharedPreference.edit()
                var gson = Gson()

                var newEmail = Email()
                newEmail.receiver = receiver
                newEmail.subject = subject
                newEmail.body = body



                var allPrefs: HashMap<String, String>  = getSharedPreferences("SentEmails", Context.MODE_PRIVATE).getAll() as HashMap<String, String>
                var emailNumber = allPrefs.size
                Log.v("email no", emailNumber.toString())

                var jsonSaved= sharedPreference.getString(emailNumber.toString(), "")
                var jsonEmailToAdd = gson.toJson(newEmail)
                Log.v("allPrefs", allPrefs.toString())
                Log.v("allPrefs", allPrefs::class.java.toString())
                Log.v("jsonEmailToAdd", jsonEmailToAdd)
                Log.v("jsonEmailToAdd", jsonEmailToAdd::class.java.toString())

                allPrefs.put(emailNumber.toString(), jsonEmailToAdd.toString())

                for ((key,value) in allPrefs) {
                    editor.putString(key, value as String?)
                }
                editor.commit()

//                remove draft present in emailDraft
                removeDraft()

//                send user back to home screen
                var goToMainActivity = Intent(this, MainActivity::class.java)
                startActivity(goToMainActivity)
                finish()

            } else {
                Toast.makeText(applicationContext, "Please make sure all entries have text.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        var receiver: String = binding.etReceiver.getText().toString()
        var subject: String = binding.etSubject.getText().toString()
        var body: String = binding.etBody.getText().toString()

        //        set shared preference
        var sharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)

//            only proceeds if there is at least one input
        if (receiver.length >= 1 || subject.length >= 1 || body.length >= 1) {
//            save to sharedPreference
            Log.v("receiver", receiver)
            Log.v("subj", subject)
            Log.v("body", body)
            var editor: SharedPreferences.Editor = sharedPreference.edit()

            editor.putString("receiver", receiver)
            editor.putString("subject", subject)
            editor.putString("body", body)
            editor.commit()
        }

        //            return to home screen
        var goToMainActivity = Intent(this, MainActivity::class.java)

        startActivity(goToMainActivity)
        finish()
    }

    override fun onStop() {
        if (!clickedSend && !discard) { //add to draft if page was closed without sending or discarding
            Log.v("onStop", "onStop and send")
            var receiver: String = binding.etReceiver.getText().toString()
            var subject: String = binding.etSubject.getText().toString()
            var body: String = binding.etBody.getText().toString()
            Log.v("receiver onDestroy", receiver)
            Log.v("subject onDestroy", subject)
            Log.v("body onDestroy", body)

            //        set shared preference
            var sharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)

//            only proceeds if there is at least one input
            if (receiver.length >= 1 || subject.length >= 1 || body.length >= 1) {
//            save to sharedPreference
                var editor: SharedPreferences.Editor = sharedPreference.edit()

                editor.putString("receiver", receiver)
                editor.putString("subject", subject)
                editor.putString("body", body)
                editor.commit()
            }
        }
        super.onStop()
    }

    private fun removeDraft() {
        Log.v("t", "removing draft")
        var emailSharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)
        var emailEditor: SharedPreferences.Editor = emailSharedPreference.edit()

        var ret: Boolean = emailEditor.clear().commit()
        Log.v("retval", ret.toString())
    }

}