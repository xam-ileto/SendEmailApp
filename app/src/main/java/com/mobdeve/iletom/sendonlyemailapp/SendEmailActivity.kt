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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        load up previous draft
        var draftSharedPreference = getSharedPreferences("EmailDraft", Context.MODE_PRIVATE)
        binding.etReceiver.setText(draftSharedPreference.getString("receiver","").toString())
        binding.etSubject.setText(draftSharedPreference.getString("subject","").toString())
        binding.etBody.setText(draftSharedPreference.getString("body","").toString())


//        set discard function
        binding.btnDiscard.setOnClickListener { view: View? ->
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
//                2 test emails
                var sharedPreference = getSharedPreferences("SentEmails", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = sharedPreference.edit()
                var gson = Gson()
//                var email1 = Email()
//                email1.receiver = "1"
//                email1.subject = "1"
//                email1.body = "1"
//                var email2 = Email()
//                email2.receiver = "2"
//                email2.subject = "2"
//                email2.body = "2"
//                editor.putString("0",gson.toJson(email1))
//                editor.putString("1",gson.toJson(email2))
//                editor.commit()



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

//                send user back to home screen
                var goToMainActivity = Intent(this, MainActivity::class.java)
                startActivity(goToMainActivity)
                finish()

//                test after
                allPrefs = getSharedPreferences("SentEmails", Context.MODE_PRIVATE).getAll() as HashMap<String, String>
                Log.v("allPrefs", allPrefs.toString())

//                var jsonArrayEmails = JSONArray()
//
//                if (jsonSaved != null) {
//                    Log.v("jsonSaved", jsonSaved::class.java.toString())
////                    for (line in jsonSaved) {
////                        jsonArrayEmails.put(line)
////                    }
//                }
//
//
//                try {
//                    if (allPrefs.size != 0) {
//                        jsonArrayEmails = JSONArray(jsonSaved)
//                    }
//                    jsonArrayEmails.put(JSONObject(jsonEmailToAdd))
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//                Log.v("jsonArrayEmails", jsonArrayEmails.toString())
//                var editor = sharedPreference.edit()
//                Log.v("t", "before for loop")
//                for (i in 0 until jsonArrayEmails.length()) {
//                    editor.putString(emailNumber.toString(),
//                        jsonArrayEmails.getJSONObject(i).toString()
//                    )
//                }
//
//                editor.commit()
            }
//            var allPrefs = getSharedPreferences("SentEmails", Context.MODE_PRIVATE).getAll()
//            Log.v("allPrefsType", allPrefs::class.java.toString())
//            for ((key,value) in allPrefs) {
//                Log.v("allPrefs item", key + "    " + value)
//            }
        }
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