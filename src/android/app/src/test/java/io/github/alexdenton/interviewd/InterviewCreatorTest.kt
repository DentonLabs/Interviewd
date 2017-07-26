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
    val exampleInterview: Interview = Interview(exampleName, listOf(exampleQuestion1, exampleQuestion2))

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

    @Test
    fun shouldBeAbleToImportAnExistingInterview(){
        val interviewCreator: InterviewCreator = InterviewCreator(exampleInterview)

        val interview: Interview = interviewCreator.create()

        assertEquals(exampleInterview, interview)
    }

    @Test
    fun shouldBeAbleToSwapQuestionsInAnInterview(){
        val expected: Interview = Interview(exampleName, listOf(exampleQuestion2, exampleQuestion1))

        val interviewCreator: InterviewCreator = InterviewCreator(exampleInterview)
        interviewCreator.swap(0, 1)
        val interview = interviewCreator.create()

        assertEquals(expected, interview)
    }

    @Test
    fun shouldBeABleToChangeTheNameOfAnInterview(){
        val expectedName = "This is the real name"

        val interviewCreator: InterviewCreator = InterviewCreator(exampleName)
        interviewCreator.changeName(expectedName)
        val interview: Interview = interviewCreator.create()

        assertEquals(expectedName, interview.name)
    }

}