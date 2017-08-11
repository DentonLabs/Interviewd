package io.github.alexdenton.interviewd

import io.github.alexdenton.interviewd.interview.Template
import io.github.alexdenton.interviewd.interview.TemplateCreator
import io.github.alexdenton.interviewd.question.Question
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Created by ryan on 7/24/17.
 */

class TemplateCreatorTest {

    val exampleName: String = "Some Test Name"
    val exampleQuestion1: Question = Question("Some Question", "Some description with text or something", 30)
    val exampleQuestion2: Question = Question("Some other question", "More descriptions of things and stuff", 45)
    val exampleTemplate: Template = Template(exampleName, listOf(exampleQuestion1, exampleQuestion2))

    @Test
    fun shouldBeAbleToHaveANameForAnInterview(){
        val templateCreator: TemplateCreator = TemplateCreator(exampleName)
        val template: Template = templateCreator.create()

        assertEquals(template.name, exampleName)
    }

    @Test
    fun shouldBeAbleToAddQuestionsToAnInterview(){
        val templateCreator: TemplateCreator = TemplateCreator("Some Name")
        templateCreator.addQuestion(exampleQuestion1)

        val template: Template = templateCreator.create()
        val questionsFromInterview = template.questions

        assertEquals(questionsFromInterview[0], exampleQuestion1)

    }

    @Test
    fun shouldBeAbleToImportAnExistingInterview(){
        val templateCreator: TemplateCreator = TemplateCreator(exampleTemplate)

        val template: Template = templateCreator.create()

        assertEquals(exampleTemplate, template)
    }

    @Test
    fun shouldBeAbleToSwapQuestionsInAnInterview(){
        val expected: Template = Template(exampleName, listOf(exampleQuestion2, exampleQuestion1))

        val templateCreator: TemplateCreator = TemplateCreator(exampleTemplate)
        templateCreator.swap(0, 1)
        val interview = templateCreator.create()

        assertEquals(expected, interview)
    }

    @Test
    fun shouldBeABleToChangeTheNameOfAnInterview(){
        val expectedName = "This is the real name"

        val templateCreator: TemplateCreator = TemplateCreator(exampleName)
        templateCreator.changeName(expectedName)
        val template: Template = templateCreator.create()

        assertEquals(expectedName, template.name)
    }

}