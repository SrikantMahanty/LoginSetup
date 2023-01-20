package com.example.loginsetup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.loginsetup.databinding.ActivityForgetpasswordBinding
import com.example.loginsetup.databinding.ActivityLoginBinding
import com.example.loginsetup.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()


        binding.Registerbtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (checkAllField()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    //if successful account is created
                    //else not get exception
                    if (it.isSuccessful) {
                        auth.signOut()
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("error: ", it.exception.toString())
                        Toast.makeText(this,"Exception occur",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun checkAllField(): Boolean {
        val email = binding.etEmail.text.toString()
        if (binding.etEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "This is required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Please Check Email format"
            return false
        }
        //password length check
        if (binding.etPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is required field"
            binding.textInputLayoutNwPassword.errorIconDrawable=null
            return false
        }
        if (binding.etPassword.length() <= 6) {
            if (binding.etPassword.text.toString() == "") {
                binding.textInputLayoutPassword.error = "Password should be atleast 8 character"
                binding.textInputLayoutNwPassword.errorIconDrawable=null
                return false

            }
        }
            if (binding.etPassword2.text.toString() == "") {
                binding.textInputLayoutNwPassword.error = "This is required field"
                binding.textInputLayoutNwPassword.errorIconDrawable=null
                return false
            }
            if (binding.etPassword.text.toString() != binding.etPassword2.text.toString()) {
                binding.textInputLayoutPassword.error = "Password donot Match"
                return false
            }
            return true
        }
    }
