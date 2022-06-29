package com.mobdeve.iletom.sendonlyemailapp.dao

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mobdeve.iletom.sendonlyemailapp.model.Email

interface EmailDAO {
    fun addEmail(email: Email)
    fun getEmails(allPrefs: HashMap<String, String>): ArrayList<Email>
    fun getLatestEmail(allPrefs: HashMap<String, String>): Email
}

class EmailDAOImpl: EmailDAO {
    private var arrayListEmails = ArrayList<Email>()
    override fun addEmail(email: Email) {
    arrayListEmails.add(email)
    }

    override fun getEmails(allPrefs: HashMap<String, String>): ArrayList<Email> {
//        Log.v("getEmails", "running code")
        var gson = Gson()
//        var newEmails = ArrayList<Email>()

        for ((key,value) in allPrefs) {
//            Log.v("key", key)
//            Log.v("value", value)
//            Log.v("valueType", value::class.java.toString())
            var newEmail: Email = gson?.fromJson(value, Email::class.java)
//            Log.v("email receiver", newEmail.receiver)
            arrayListEmails.add(newEmail)
        }

        Log.v("arrayListEmails", arrayListEmails.toString())
        return arrayListEmails
    }

    override fun getLatestEmail(allPrefs: HashMap<String, String>): Email {
        this.getEmails(allPrefs)
        return this.arrayListEmails.get(arrayListEmails.size - 1)
    }

}