package io.github.alexdenton.interviewd

import io.github.alexdenton.interviewd.interview.Interview
import io.github.alexdenton.interviewd.interview.InterviewCreator
import io.github.alexdenton.interviewd.question.Question
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Created by ryan on 7/24/17.
 */

class InterviewCreatorTest {

    val exampleName: String = "Some Test Name"
    val exampleQuestion1: Question = Question("Some Question", "Some description with text or something", 30)
    val exampleQuestion2: Question = Question("Some other question", "More descriptions of things and stuff", 45)

    @Test
    fun shouldBeAbleToHaveANameForAnInterview(){
        val interviewCreator: InterviewCreator = InterviewCreator(exampleName)
        val interview: Interview = interviewCreator.create()

        assertEquals(interview.name, exampleName)
    }

    @Test
    fun shouldBeAbleToAddQuestionsToAnInterview(){
        val interviewCreator: InterviewCreator = InterviewCreator("Some Name")
        interviewCreator.addQuestion(exampleQuestion1)

        val interview: Interview = interviewCreator.create()
        val questionsFromInterview = interview.questions

        assertEquals(questionsFromInterview[0], exampleQuestion1)

    }
}