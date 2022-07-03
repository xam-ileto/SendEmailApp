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
        var gson = Gson()

        for ((key,value) in allPrefs) {
            var newEmail: Email = gson?.fromJson(value, Email::class.java)
            arrayListEmails.add(newEmail)
        }

        return arrayListEmails
    }

    override fun getLatestEmail(allPrefs: HashMap<String, String>): Email {
        this.getEmails(allPrefs)
        return this.arrayListEmails.get(arrayListEmails.size - 1)
    }

}