package com.example.quiz.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Quiz() {
    constructor(name: String): this() {
        this.name = name
    }

    @Id
    var id: Long = 0

    lateinit var name: String

    lateinit var questions: ToMany<Question>

    fun getQuestions() = questions.listFactory.createList<Question>()
}