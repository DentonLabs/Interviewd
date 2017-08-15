package io.github.alexdenton.interviewd

import io.github.alexdenton.interviewd.question.Question
import io.github.alexdenton.interviewd.question.QuestionManager
import io.github.alexdenton.interviewd.question.QuestionSubmit
import org.junit.Test
import org.junit.Assert.assertEquals

class QuestionManagerTest {

    class MockQuestionSubmit : QuestionSubmit {
        var question: Question = Question(1, "Some Default", "Text or something", 55)
        override fun submit(question: Question) {
            this.question = question
        }
    }

    val mockQuestionSubmit: MockQuestionSubmit = MockQuestionSubmit()
    var questionManager: QuestionManager = QuestionManager(mockQuestionSubmit)


    @Test
    fun submittedQuestionShouldBeReceivedUnmodified() {
        val expected = Question(2, "Test Name", "Test Description", 43)
        questionManager.submit(expected)
        val actual = mockQuestionSubmit.question

        assertEquals(expected, actual)
    }
}

