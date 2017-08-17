package io.github.alexdenton.interviewd.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.reactivex.Single
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * Created by ryan on 8/17/17.
 */
class DemoApi(val context: Context) : InterviewdApiService {
    val gson: Gson = Gson()
    val questionsFilename = "questions"
    val templatesFilename = "templates"

    init {
        if (!context.fileList().contains(questionsFilename))
            context.openFileOutput(questionsFilename, Context.MODE_PRIVATE).close()

        if (!context.fileList().contains(templatesFilename))
            context.openFileOutput(templatesFilename, Context.MODE_PRIVATE).close()
    }


    override fun getQuestions(): Single<List<QuestionDto>> {
        val reader = context.openFileInput(questionsFilename)
        val json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Questions available"))

        val list: List<QuestionDto> = gson.fromJson(json, object : TypeToken<List<QuestionDto>>() {}.type)

        return Single.just(list)
    }

    override fun getQuestion(id: Int): Single<QuestionDto> {
        val reader = InputStreamReader(context.openFileInput(questionsFilename))
        val json = reader.readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Questions available"))

        val list: List<QuestionDto> = gson.fromJson(json, object : TypeToken<List<QuestionDto>>() {}.type)

        return Single.just(list.find { (matchingId) -> matchingId == id })
    }

    override fun createQuestion(question: QuestionDto): Single<QuestionDto> {
        val reader = context.openFileInput(questionsFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<QuestionDto> = gson.fromJson(json, object : TypeToken<List<QuestionDto>>() {}.type)

        val mutableList = list.toMutableList()
        val toAdd = QuestionDto(mutableList.size + 1, question.name, question.description)
        mutableList.add(toAdd)
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(questionsFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(toAdd)
    }

    override fun getTemplates(): Single<List<TemplateDto>> {
        val reader = context.openFileInput(templatesFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Templates available"))

        var list: List<TemplateDto> = gson.fromJson(json, object : TypeToken<List<TemplateDto>>() {}.type)

        return Single.just(list)
    }

    override fun createTemplate(template: TemplateDto): Single<TemplateDto> {
        val reader = context.openFileInput(templatesFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<TemplateDto> = gson.fromJson(json, object : TypeToken<List<TemplateDto>>() {}.type)

        val mutableList = list.toMutableList()
        val toAdd = TemplateDto(template.name, template.questions, mutableList.size + 1)
        mutableList.add(toAdd)
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(templatesFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(toAdd)
    }
}