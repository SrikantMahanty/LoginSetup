package com.example.loginsetup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginsetup.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.lang.ref.Reference

class ProfileSetUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var  databse: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.HeightPicker.minValue=100
        binding.HeightPicker.maxValue=250
        binding.WeightPicker.minValue=30
        binding.WeightPicker.maxValue=150

        binding.WeightPicker.setOnValueChangedListener {
            _, _, _ ->
            calculateBMI()
        }

        //create instance
        auth=FirebaseAuth.getInstance()
        val uid=auth.currentUser?.uid
        databse= FirebaseDatabase.getInstance()
        storage=FirebaseStorage.getInstance()


        binding.button.setOnClickListener{
            val name=binding.nametv.text.toString()
            val age=binding.Agetv.text.toString()
            val address=binding.addtv.text.toString()
            val Bmi=binding.resulttv.text.toString()
            val user=User(name,age,address,Bmi)
            if(uid!=null){
                databse.reference.child(uid).setValue(user).addOnCompleteListener {
                    if(it.isSuccessful){
                               binding.nametv.text.clear()
                        binding.Agetv.text.clear()
                        binding.addtv.text.clear()
                        binding.resulttv.text.get(1)
                        Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else{
                        Toast.makeText(this@ProfileSetUpActivity,
                            "Failed to update Profile",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun calculateBMI() {
       val height=binding.HeightPicker.value
         val dheight=height.toDouble()/100

        val weight=binding.HeightPicker.value
        val bmi=weight.toDouble()/(dheight*dheight)

        binding.resulttv.text=String.format("Your BMI  is :%.2f",bmi)

    }
}