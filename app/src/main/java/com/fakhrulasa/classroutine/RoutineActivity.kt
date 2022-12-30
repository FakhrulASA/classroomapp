package com.fakhrulasa.classroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fakhrulasa.classroutine.databinding.ActivityRoutineBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RoutineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar.apply {
            this!!.title = "Routine Activity"
            this.setDisplayHomeAsUpEnabled(true)
        }

        binding.msg.text= intent.getStringExtra("info")

        binding.buttonLogout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
}