package com.fakhrulasa.classroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fakhrulasa.classroutine.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainIntent = Intent(this,RoutineActivity::class.java)

        supportActionBar.apply {
            this!!.title = "Login"
            this.setDisplayHomeAsUpEnabled(true)
        }
        auth = FirebaseAuth.getInstance();

        binding.buttonLogin.setOnClickListener {
            binding.progressHorizontal.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(this) { task ->
                binding.progressHorizontal.visibility = View.INVISIBLE
                if (task.isSuccessful) {
                    Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    mainIntent.putExtra("info",
                        "Login Successful for "+
                         binding.email.text.toString().split("@")[0])
                    startActivity(mainIntent)
                } else {
                    Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.buttonRegPage.paint?.isUnderlineText = true
        binding.buttonRegPage.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }

    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            mainIntent.putExtra("info",
                "Login Successful for "+
                currentUser.email!!.split("@")[0])
            startActivity(mainIntent)
        }
    }

}