package com.example.loginsetup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsetup.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //use to remove /hideactionbar
        supportActionBar?.hide()

// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener{
            val email=binding.loginEmail.text.toString()
            val password=binding.loginPassword.text.toString()
            if(checkAllfield()){
               auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                   if(it.isSuccessful){
                       //if successful
                       Toast.makeText(this,"Successfully Logged in",Toast.LENGTH_SHORT).show()
                       val intent=Intent(this,ProfileSetUpActivity::class.java)
                       startActivity(intent)
                       //use to destroy  activity
                       finish()
                   }else{
                       //not sign in
                       Log.e("Error",it.exception.toString())
                       Toast.makeText(this,"Log in Failed",Toast.LENGTH_SHORT).show()
                   }
               }
            }

        }

        binding.Registertv.setOnClickListener{
                 val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.forgetpassword.setOnClickListener{
            val intent=Intent(this,Forgetpassword::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkAllfield():Boolean{
        val email = binding.loginEmail.text.toString()
        if (binding.loginEmail.text.toString() == "") {

            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return false
        }
        //password length check
        if (binding.loginPassword.text.toString() == "") {
            return false
        }
        if (binding.loginPassword.length() <= 6) {
            if (binding.loginPassword.text.toString() == "") {
                return false

            }
        }
        return true

    }
}