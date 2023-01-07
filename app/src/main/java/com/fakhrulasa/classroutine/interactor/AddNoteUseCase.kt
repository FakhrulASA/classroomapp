package com.fakhrulasa.classroutine.interactor

import com.fakhrulasa.classroutine.model.AddNoteModel
import com.fakhrulasa.classroutine.repo.AddNoteRepo

object AddNoteUseCase {
    fun invoke(addNoteModel: AddNoteModel, success: (String) -> Unit, failed: (String) -> Unit) {
        var addNoteRepo = AddNoteRepo()
        addNoteRepo.addNoteToServer(addNoteModel, {
            success.invoke("Successfully Saved")
        }, {
            failed.invoke("Data cannot be saved!")
        })
    }
}