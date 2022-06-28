package com.mobdeve.iletom.sendonlyemailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.iletom.sendonlyemailapp.databinding.ActivityLatestEmailBinding

class LatestEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLatestEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}