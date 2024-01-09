package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.room.Room
import com.example.todoapp.databinding.ActivitySignBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignActivity : BaseActivity() {

    private var binding: ActivitySignBinding? = null
    private lateinit var auth: FirebaseAuth

    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth=Firebase.auth

        database= Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()
        GlobalScope.launch {
            DataObject.listdata=database.dao().getTasks() as MutableList<CardInfo>
        }

        binding?.tvRegister?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding?.tvForgotPassword?.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

        binding?.btnSignIn?.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser(){
        val email = binding?.etSignInEmail?.text.toString()
        val password = binding?.etSignInPassword?.text.toString()
        if(validateForm(email, password)){
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        hideprogressBar()
                    }
                    else
                    {
                        showToast(this, "Can't login currently. try after sometime")
                        hideprogressBar()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when{
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error="Enter valid email address"
                false
            }

            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error="Enter password"
                false
            }
            else -> {true}
        }
    }
}