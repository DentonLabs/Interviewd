package io.github.alexdenton.interviewd.api

import io.github.alexdenton.interviewd.api.dto.QuestionDto
import io.github.alexdenton.interviewd.api.dto.TemplateDto
import io.reactivex.Single

/**
 * Created by ryan on 8/17/17.
 */
class DemoApi : InterviewdApiService {
    override fun getQuestions(): Single<List<QuestionDto>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getQuestion(id: Int): Single<QuestionDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createQuestion(question: QuestionDto): Single<QuestionDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTemplates(): Single<List<TemplateDto>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTemplate(template: TemplateDto): Single<TemplateDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}