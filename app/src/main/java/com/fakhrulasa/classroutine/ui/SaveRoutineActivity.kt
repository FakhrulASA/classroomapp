package com.fakhrulasa.classroutine.ui

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.classroutine.databinding.ActivitySaveRoutineBinding
import com.fakhrulasa.classroutine.model.AddNoteModel
import com.fakhrulasa.classroutine.viewmodel.AddNoteViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

class SaveRoutineActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    val db = Firebase.firestore

    private lateinit var binding: ActivitySaveRoutineBinding
    private val vm : AddNoteViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.N)
    var now: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    var dpd: DatePickerDialog = DatePickerDialog.newInstance(
        this,
        now[Calendar.YEAR],  // Initial year selection
        now[Calendar.MONTH],  // Initial month selection
        now[Calendar.DAY_OF_MONTH] // Inital day selection
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRoutineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvClassTime.setOnClickListener {
            dpd.show(supportFragmentManager, "Select your class time")
        }

        binding.button2.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            val classN = binding.etClassName.text.toString()
            val classT = binding.tvClassTime.text.toString()
            val teacherN = binding.etTeacherName.text.toString()

            vm.saveNote(AddNoteModel(
                classN,classT,teacherN
            ),{
                binding.progressBar.visibility = View.GONE
              showToastMessage(this, it)
            },{
                binding.progressBar.visibility = View.GONE
                showToastMessage(this, it)
            })
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        binding.tvClassTime.text = hourOfDay.toString() + minute.toString()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        binding.tvClassTime.text =
            year.toString() + "/" + monthOfYear.toString() + "/" + dayOfMonth.toString()
    }
}