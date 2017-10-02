package io.github.alexdenton.interviewd.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.alexdenton.interviewd.api.dto.CandidateDto
import io.github.alexdenton.interviewd.api.dto.InterviewDto
import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.github.alexdenton.interviewd.interview.Candidate
import io.github.alexdenton.interviewd.interview.InterviewStatus
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
    val candidatesFilename = "candidates"
    val interviewsFilename = "interviews"

    init {
        if (!context.fileList().contains(questionsFilename))
            context.openFileOutput(questionsFilename, Context.MODE_PRIVATE).close()

        if (!context.fileList().contains(templatesFilename))
            context.openFileOutput(templatesFilename, Context.MODE_PRIVATE).close()

        if (!context.fileList().contains(candidatesFilename))
            context.openFileOutput(candidatesFilename, Context.MODE_PRIVATE).close()

        if (!context.fileList().contains(interviewsFilename))
            context.openFileOutput(interviewsFilename, Context.MODE_PRIVATE).close()
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
        val toAdd = QuestionDto(mutableList.size + 1, question.name, question.description, question.estimate)
        mutableList.add(toAdd)
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(questionsFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(toAdd)
    }

    override fun patchQuestion(id: Int, patch: QuestionDto): Single<QuestionDto> {
        val reader = context.openFileInput(questionsFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<QuestionDto> = gson.fromJson(json, object : TypeToken<List<QuestionDto>>() {}.type)

        val mutableList = list.toMutableList()
        mutableList[mutableList.indexOfFirst { it.id == id }] = patch
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(questionsFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(patch)
    }

    override fun getTemplates(): Single<List<TemplateDto>> {
        val reader = context.openFileInput(templatesFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Templates available"))

        var list: List<TemplateDto> = gson.fromJson(json, object : TypeToken<List<TemplateDto>>() {}.type)

        return Single.just(list)
    }

    override fun getTemplate(id: Int): Single<TemplateDto> {
        val reader = InputStreamReader(context.openFileInput(templatesFilename))
        val json = reader.readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Templates available"))

        val list: List<TemplateDto> = gson.fromJson(json, object : TypeToken<List<TemplateDto>>() {}.type)

        return Single.just(list.find { it.id == id })
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

    override fun getCandidates(): Single<List<CandidateDto>> {
        val reader = context.openFileInput(candidatesFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Candidates available"))

        var list: List<CandidateDto> = gson.fromJson(json, object : TypeToken<List<CandidateDto>>() {}.type)

        return Single.just(list)
    }

    override fun getCandidate(id: Int): Single<CandidateDto> {
        val reader = InputStreamReader(context.openFileInput(candidatesFilename))
        val json = reader.readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Candidates available"))

        val list: List<CandidateDto> = gson.fromJson(json, object : TypeToken<List<CandidateDto>>() {}.type)

        return Single.just(list.find { candidate -> candidate.id == id })
    }

    override fun createCandidate(candidate: CandidateDto): Single<CandidateDto> {
        val reader = context.openFileInput(candidatesFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<CandidateDto> = gson.fromJson(json, object : TypeToken<List<CandidateDto>>() {}.type)

        val mutableList = list.toMutableList()
        val toAdd = CandidateDto(mutableList.size + 1, candidate.firstName, candidate.lastName)
        mutableList.add(toAdd)
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(candidatesFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(toAdd)
    }

    override fun getInterviews(): Single<List<InterviewDto>> {
        val reader = context.openFileInput(interviewsFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Interviews available"))

        var list: List<InterviewDto> = gson.fromJson(json, object : TypeToken<List<InterviewDto>>() {}.type)

        return Single.just(list)
    }

    override fun getInterview(id: Int): Single<InterviewDto> {
        val reader = InputStreamReader(context.openFileInput(interviewsFilename))
        val json = reader.readText()
        reader.close()

        if(json == "") return Single.error(Throwable("No Interviews available"))

        val list: List<InterviewDto> = gson.fromJson(json, object : TypeToken<List<InterviewDto>>() {}.type)

        return Single.just(list.find { interview -> interview.id == id })
    }

    override fun createInterview(interview: InterviewDto): Single<InterviewDto> {
        val reader = context.openFileInput(interviewsFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<InterviewDto> = gson.fromJson(json, object : TypeToken<List<InterviewDto>>() {}.type)

        val mutableList = list.toMutableList()
        val toAdd = InterviewDto(mutableList.size + 1, interview.candidate, interview.name, interview.questions, interview.status)
        mutableList.add(toAdd)
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(interviewsFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(toAdd)
    }

    override fun markInterviewAsComplete(id: Int): Single<InterviewDto> {
        val reader = context.openFileInput(interviewsFilename)
        var json = reader.bufferedReader().readText()
        reader.close()

        if(json == "") json = "[]"

        var list: List<InterviewDto> = gson.fromJson(json, object  : TypeToken<List<InterviewDto>>() {}.type)

        val mutableList = list.toMutableList()
        mutableList.map { if (it.id == id) it.status = InterviewStatus.COMPLETE}
        list = mutableList.toList()

        val writer = OutputStreamWriter(context.openFileOutput(interviewsFilename, Context.MODE_PRIVATE))
        gson.toJson(list, writer)
        writer.close()

        return Single.just(list.single { it.id == id })

    }
}