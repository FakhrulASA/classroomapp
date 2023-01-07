package com.fakhrulasa.classroutine

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivityAddClassBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AddClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddClassBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar.apply {
            this!!.title = "Add Class"
            this.setDisplayHomeAsUpEnabled(true)
        }
        val db = Firebase.firestore

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setTitleText("Select date")
                .build()
        binding.time.setOnClickListener{
            datePicker.show(supportFragmentManager, "tag");
        }
        datePicker.addOnPositiveButtonClickListener {
           // val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")

            val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale("BD"))
            val dateString = formatter.format(Date(it))
            binding.time.setText(dateString)
        }
        binding.btnSave.setOnClickListener {
            val data= ClassItem(
                binding.nm.editableText.toString(),
                binding.time.editableText.toString(),
                binding.initial.editableText.toString()
            )
            db.collection("routine")
                .add(data)
                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!")
                    startActivity(Intent(this, RoutineActivity::class.java))
                }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
        }




    }
}