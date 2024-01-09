package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.databinding.ActivityGetStartredBinding

class GetStartredActivity : AppCompatActivity() {

    private var binding: ActivityGetStartredBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityGetStartredBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.cvGetStarted?.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        }
//        val auth=Firebase.auth
//        if (auth.currentUser!=null){
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
    }
}