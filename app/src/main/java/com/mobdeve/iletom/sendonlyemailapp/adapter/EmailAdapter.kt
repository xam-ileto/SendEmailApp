package com.mobdeve.iletom.sendonlyemailapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.iletom.sendonlyemailapp.databinding.EmailCardItemBinding
import com.mobdeve.iletom.sendonlyemailapp.model.Email

class EmailAdapter: RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {
    private var emailArrayList = ArrayList<Email>()
    private lateinit var context: Context

    constructor(context: Context, emailArrayList: ArrayList<Email>) {
        this.context = context
        this.emailArrayList = emailArrayList
    }

    fun removeProfile(position: Int) {
        emailArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return emailArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            EmailAdapter.EmailViewHolder {
        val itemBinding = EmailCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EmailViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EmailAdapter.EmailViewHolder, position: Int ) {
        holder.bindProfile(emailArrayList[position])

    }

    inner class EmailViewHolder(private val itemBinding: EmailCardItemBinding) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{

        var email = Email()

        init {
            itemView.setOnClickListener(this)
        }

        fun bindProfile(email: Email) {
            this.email = email
            
            itemBinding.tvEmailReceiver.text = prepareTextForDisplay(this.email.receiver)
            itemBinding.tvEmailSubject.text = prepareTextForDisplay(this.email.subject)
            itemBinding.tvEmailBody.text = prepareTextForDisplay(this.email.body)
        }

        override fun onClick(p0: View?) {
//            clicking recycler view should do nothing
        }

        private fun prepareTextForDisplay(x: String): String? {
            return x.replace("(\\n)+".toRegex(), " ")
        }

    }


}