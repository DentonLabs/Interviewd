package io.github.alexdenton.interviewd.objectbox

import io.github.alexdenton.interviewd.objectbox.dto.CandidateEntity
import io.github.alexdenton.interviewd.objectbox.dto.InterviewEntity
import io.github.alexdenton.interviewd.objectbox.dto.QuestionEntity
import io.github.alexdenton.interviewd.objectbox.dto.TemplateEntity
import io.reactivex.Single

/**
 * Created by ryan on 8/11/17.
 */
interface InterviewdObjectboxApi {

    fun getQuestions(): Single<List<QuestionEntity>>

    fun getQuestion(id: Long): Single<QuestionEntity>

    fun createQuestion(question: QuestionEntity): Single<QuestionEntity>

    fun deleteQuestion(id: Long): Single<QuestionEntity>

    fun patchQuestion(id: Long, patch: QuestionEntity): Single<QuestionEntity>

    fun getTemplates(): Single<List<TemplateEntity>>

    fun getTemplate(id: Long): Single<TemplateEntity>

    fun createTemplate(template: TemplateEntity): Single<TemplateEntity>

    fun deleteTemplate(id: Long): Single<TemplateEntity>

    fun patchTemplate(id: Long, patch: TemplateEntity): Single<TemplateEntity>

    fun getCandidates(): Single<List<CandidateEntity>>

    fun getCandidate(id: Long): Single<CandidateEntity>

    fun createCandidate(candidate: CandidateEntity): Single<CandidateEntity>

    fun deleteCandidate(id: Long): Single<CandidateEntity>

    fun patchCandidate(id: Long, patch: CandidateEntity): Single<CandidateEntity>

    fun getInterviews(): Single<List<InterviewEntity>>

    fun getInterview(id: Long): Single<InterviewEntity>

    fun createInterview(interview: InterviewEntity): Single<InterviewEntity>

    fun deleteInterview(id: Long): Single<InterviewEntity>

    fun patchInterview(id: Long, patch: InterviewEntity): Single<InterviewEntity>
}