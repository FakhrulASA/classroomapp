package com.fakhrulasa.classroutine

class ClassItem {
    var id: String=""
    var nm: String=""
    var time: String=""
    var initial: String=""

    constructor() {}
    constructor(
        nm: String,
        time: String,
        initial: String
    ) {
        this.nm = nm
        this.time = time
        this.initial = initial
    }
}