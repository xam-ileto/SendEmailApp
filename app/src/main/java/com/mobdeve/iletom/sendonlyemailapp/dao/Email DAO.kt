package com.mobdeve.iletom.sendonlyemailapp.dao

import com.mobdeve.iletom.sendonlyemailapp.model.Email

interface EmailDAO {
    fun addEmail(email: Email)
    fun getEmails(): ArrayList<Email>
}

class EmailDAOImpl: EmailDAO {
    private var arrayListEmails = ArrayList<Email>()
    override fun addEmail(email: Email) {
    arrayListEmails.add(email)
    }

    override fun getEmails(): ArrayList<Email> {
        return arrayListEmails
    }

}