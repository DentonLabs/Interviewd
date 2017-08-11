package io.github.alexdenton.interviewd.interview

import io.github.alexdenton.interviewd.question.Question

/**
 * Created by ryan on 7/24/17.
 */
class TemplateCreator(var name: String) {
    constructor(template: Template) : this(template.name) {
        for (question in template.questions) addQuestion(question)
    }

    val internalList = mutableListOf<Question>()

    fun addQuestion(question: Question) = internalList.add(question)

    fun create(): Template = Template(name, internalList.toList())

    fun swap(indexOne: Int, indexTwo: Int) = internalList.apply {
        val temp: Question = this[indexOne]
        this[indexOne] = this[indexTwo]
        this[indexTwo] = temp
    }

    fun changeName(newName: String) {
        name = newName
    }

}