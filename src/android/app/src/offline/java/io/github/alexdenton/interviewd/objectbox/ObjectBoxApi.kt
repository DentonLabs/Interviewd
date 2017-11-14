package io.github.alexdenton.interviewd.objectbox

import io.github.alexdenton.interviewd.objectbox.dto.CandidateEntity
import io.github.alexdenton.interviewd.objectbox.dto.InterviewEntity
import io.github.alexdenton.interviewd.objectbox.dto.QuestionEntity
import io.github.alexdenton.interviewd.objectbox.dto.TemplateEntity
import io.objectbox.BoxStore
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class ObjectBoxApi(boxStore: BoxStore) : InterviewdObjectboxApi {

    val questionBox = boxStore.boxFor(QuestionEntity::class.java)

    override fun getQuestions(): Single<List<QuestionEntity>> {
        return Single.just(questionBox.all)
    }

    override fun getQuestion(id: Long): Single<QuestionEntity> {
        return Single.just(questionBox[id])
    }

    override fun createQuestion(question: QuestionEntity): Single<QuestionEntity> {
        val questionEntity = QuestionEntity(question.id, question.name, question.description, question.estimate)
        questionBox.put(questionEntity)
        return Single.just(questionEntity)
    }

    override fun deleteQuestion(id: Long): Single<QuestionEntity> {
        val questionEntity = questionBox.get(id)
        questionBox.remove(id)
        return Single.just(questionEntity)
    }

    override fun patchQuestion(id: Long, patch: QuestionEntity): Single<QuestionEntity> {
        val patchedQuestionEntity = questionBox.get(id).copy(name = patch.name, description = patch.description, estimate = patch.estimate)
        questionBox.put(patchedQuestionEntity)
        return Single.just(patchedQuestionEntity)
    }

    override fun getTemplates(): Single<List<TemplateEntity>> {
        return Single.never()
    }

    override fun getTemplate(id: Long): Single<TemplateEntity> {
        return Single.never()
    }

    override fun createTemplate(template: TemplateEntity): Single<TemplateEntity> {
        return Single.never()
    }

    override fun deleteTemplate(id: Long): Single<TemplateEntity> {
        return Single.never()
    }

    override fun patchTemplate(id: Long, patch: TemplateEntity): Single<TemplateEntity> {
        return Single.never()
    }

    override fun getCandidates(): Single<List<CandidateEntity>> {
        return Single.never()
    }

    override fun getCandidate(id: Long): Single<CandidateEntity> {
        return Single.never()
    }

    override fun createCandidate(candidate: CandidateEntity): Single<CandidateEntity> {
        return Single.never()
    }

    override fun deleteCandidate(id: Long): Single<CandidateEntity> {
        return Single.never()
    }

    override fun patchCandidate(id: Long, patch: CandidateEntity): Single<CandidateEntity> {
        return Single.never()
    }

    override fun getInterviews(): Single<List<InterviewEntity>> {
        return Single.never()
    }

    override fun getInterview(id: Long): Single<InterviewEntity> {
        return Single.never()
    }

    override fun createInterview(interview: InterviewEntity): Single<InterviewEntity> {
        return Single.never()
    }

    override fun deleteInterview(id: Long): Single<InterviewEntity> {
        return Single.never()
    }

    override fun patchInterview(id: Long, patch: InterviewEntity): Single<InterviewEntity> {
        return Single.never()
    }
}