package com.fakhrulasa.classroutine

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivityRoutineBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RoutineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoutineBinding
    private val classList= mutableListOf<ClassItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar.apply {
            this!!.title = "All Routines"
            this.setDisplayHomeAsUpEnabled(true)
        }

        val db = Firebase.firestore

        db.collection("routine")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val xx: ClassItem = document.toObject(ClassItem::class.java)
                    xx.id=document.id
                    classList.add(xx)

                }
            }
            .addOnCompleteListener {
                binding.recyclerView.adapter=RoutineRecyclerViewAdapter(classList)
                Log.d("TAG", classList.size.toString())

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }

        binding.extendedFab.shrink()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.extendedFab.extend()
        }, 1500)
        binding.extendedFab.setOnClickListener {
            startActivity(Intent(this, AddClassActivity::class.java))

        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        val item = menu.findItem(R.id.btnLogout)
        item.setOnMenuItemClickListener {
            Firebase.auth.signOut()
            Toast.makeText(applicationContext, "hi", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            false
        }
        return true
    }
}