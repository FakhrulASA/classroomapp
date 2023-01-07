package com.fakhrulasa.classroutine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fakhrulasa.classroutine.databinding.RoutineItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RoutineRecyclerViewAdapter(private val classList: List<ClassItem>) :
    RecyclerView.Adapter<RoutineRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: RoutineItemBinding, private val contest: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(list: List<ClassItem>, position: Int) {
            binding.nm.text = list[position].nm
            binding.time.text = list[position].time
            binding.teacherInitial.text = list[position].initial
            val db = Firebase.firestore

            binding.btnDelete.setOnClickListener {
                db.collection("routine").document(list[position].id)
                    .delete()
                    .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error deleting document", e) }
                contest.startActivity(Intent(contest, RoutineActivity::class.java))

            }
        }
        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RoutineItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        ),
        viewGroup.context
    )

    override fun getItemCount() = classList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(classList, position)
    }
}