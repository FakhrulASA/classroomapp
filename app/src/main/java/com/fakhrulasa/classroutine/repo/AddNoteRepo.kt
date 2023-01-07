package com.fakhrulasa.classroutine.repo

import android.view.View
import com.fakhrulasa.classroutine.model.AddNoteModel
import com.fakhrulasa.classroutine.ui.showToastMessage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNoteRepo {
    val db = Firebase.firestore
    var classObject = HashMap<String, String>()
    fun addNoteToServer(addNoteModel: AddNoteModel,success:()->Unit, failed:()->Unit){
        classObject["Class Name"] = addNoteModel.className
        classObject["Class Time"] = addNoteModel.classTime
        classObject["Teacher Name"] = addNoteModel.classTeacher

        db.collection("Class")
            .add(classObject)
            .addOnSuccessListener { documentReference ->
                success.invoke()
            }
            .addOnFailureListener { e ->
                failed.invoke()
            }
    }
}