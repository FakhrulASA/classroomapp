package com.fakhrulasa.classroutine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar.apply {
            this!!.title = "Registration"
            this.setDisplayHomeAsUpEnabled(true)
        }

        auth = Firebase.auth;

        binding.button.setOnClickListener {
            binding.progressHorizontal.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(this) { task->
                binding.progressHorizontal.visibility = View.GONE
                if(task.isSuccessful){
                   Toast.makeText(this,"Registered Successfully", Toast.LENGTH_LONG).show()
               } else{
                   Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
               }
            }
        }
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        finish();
        return super.navigateUpTo(upIntent)
    }
}