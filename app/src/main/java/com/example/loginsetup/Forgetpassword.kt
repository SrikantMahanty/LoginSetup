package com.example.loginsetup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsetup.databinding.ActivityForgetpasswordBinding
import com.google.firebase.auth.FirebaseAuth

class Forgetpassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityForgetpasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.forgotbtn.setOnClickListener {
            val email = binding.etfemail.text.toString()
            if (checkAllfield()) {

                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)

                        finish()
                    }

                }
            }
        }
    }

    private fun checkAllfield(): Boolean {
        val email = binding.etfemail.text.toString()
        if (binding.etfemail.text.toString() == "") {

            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return false
        }
        return true
    }
}