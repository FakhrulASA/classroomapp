package com.fakhrulasa.classroutine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivityRegistrationBinding
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
            binding.progressHorizontal.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(this) { task->
                binding.progressHorizontal.visibility = View.GONE
                if(task.isSuccessful){
                   Toast.makeText(this,"Registered Successfully", Toast.LENGTH_LONG).show()
                    val mainIntent= Intent(this,RoutineActivity::class.java)
                    mainIntent.putExtra("info",
                        "Registered Successful for "+
                        binding.email.text.toString().split("@")[0])
                    startActivity(mainIntent)
                } else{
                   Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
               }
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