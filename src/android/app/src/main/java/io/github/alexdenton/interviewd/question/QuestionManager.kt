package io.github.alexdenton.interviewd.question

class QuestionManager(val questionSubmit: QuestionSubmit) {
    
    fun submit(question: Question) =
            questionSubmit.submit(question)


}