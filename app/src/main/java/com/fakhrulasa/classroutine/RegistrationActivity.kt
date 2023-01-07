package com.fakhrulasa.classroutine

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivityRegistrationBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar.apply {
            this!!.title = "Registration"
            this.setDisplayHomeAsUpEnabled(true)
        }

        auth = Firebase.auth



        binding.button.setOnClickListener {
            val pass=binding.password.text.toString()
            val rePass=binding.rePassword.text.toString()
            if(binding.email.text.toString().isNotBlank()
                && pass.isNotBlank() && rePass.isNotBlank()) {
                if(pass != rePass) {
                    binding.second2.endIconMode= TextInputLayout.END_ICON_NONE
                    binding.rePassword.error = "Password didn't match."
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.rePassword.error=null
                        binding.second2.endIconMode= TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }, 1500)
                }
                else{
                    binding.progressHorizontal.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(
                        binding.email.text.toString(),
                        binding.password.text.toString())
                        .addOnCompleteListener(this) { task->
                            binding.progressHorizontal.visibility = View.GONE
                            if(task.isSuccessful){
                                Toast.makeText(this,"Registered Successfully", Toast.LENGTH_LONG).show()
                                val mainIntent= Intent(this,RoutineActivity::class.java)
                                startActivity(mainIntent)
                            } else{
                                Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
            else {
                Toast.makeText(this,"Can't be empty.", Toast.LENGTH_SHORT).show()
            }


        }

        binding.buttonLoginPage.paint?.isUnderlineText = true
        binding.buttonLoginPage.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        finish()
        return super.navigateUpTo(upIntent)
    }
}