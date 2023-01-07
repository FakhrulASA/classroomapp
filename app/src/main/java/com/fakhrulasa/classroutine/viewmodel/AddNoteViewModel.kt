package com.fakhrulasa.classroutine.viewmodel

import androidx.lifecycle.ViewModel
import com.fakhrulasa.classroutine.interactor.AddNoteUseCase.invoke
import com.fakhrulasa.classroutine.model.AddNoteModel

class AddNoteViewModel : ViewModel() {

    fun saveNote(addNoteModel: AddNoteModel, success: (String) -> Unit, failed: (String) -> Unit){
        invoke(addNoteModel,{ s->
            success(s)
        },{f->
            failed(f)
        })
    }
}