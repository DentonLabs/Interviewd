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
    val candidateBox = boxStore.boxFor(CandidateEntity::class.java)
    val templateBox = boxStore.boxFor(TemplateEntity::class.java)
    val interviewBox = boxStore.boxFor(InterviewEntity::class.java)

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
        return Single.just(templateBox.all)
    }

    override fun getTemplate(id: Long): Single<TemplateEntity> {
        val entity = templateBox[id]
        return if(entity == null)
            Single.error(Exception("Template not found"))
        else
            Single.just(entity)
    }

    override fun createTemplate(template: TemplateEntity): Single<TemplateEntity> {
        templateBox.put(template)
        return Single.just(template)
    }

    override fun deleteTemplate(id: Long): Single<TemplateEntity> {
        val templateEntity = templateBox.get(id)
        templateBox.remove(id)
        return Single.just(templateEntity)
    }

    override fun patchTemplate(id: Long, patch: TemplateEntity): Single<TemplateEntity> {
        templateBox.put(patch)
        return Single.just(patch)
    }

    override fun getCandidates(): Single<List<CandidateEntity>> {
        return Single.just(candidateBox.all)
    }

    override fun getCandidate(id: Long): Single<CandidateEntity> {
        return Single.just(candidateBox[id])
    }

    override fun createCandidate(candidate: CandidateEntity): Single<CandidateEntity> {
        candidateBox.put(candidate)
        return Single.just(candidate)
    }

    override fun deleteCandidate(id: Long): Single<CandidateEntity> {
        val candidateEntity = candidateBox.get(id)
        candidateBox.remove(id)
        return Single.just(candidateEntity)
    }

    override fun patchCandidate(id: Long, patch: CandidateEntity): Single<CandidateEntity> {
        candidateBox.put(patch)
        return Single.just(patch)
    }

    override fun getInterviews(): Single<List<InterviewEntity>> {
        return Single.just(interviewBox.all)
    }

    override fun getInterview(id: Long): Single<InterviewEntity> {
        val entity = interviewBox[id]
        return if(entity == null)
            Single.error(Exception("Interview not found"))
        else
            Single.just(entity)
    }

    override fun createInterview(interview: InterviewEntity): Single<InterviewEntity> {
        interviewBox.put(interview)
        return Single.just(interview)
    }

    override fun deleteInterview(id: Long): Single<InterviewEntity> {
        val entity = interviewBox.get(id)
        interviewBox.remove(id)
        return Single.just(entity)
    }

    override fun patchInterview(id: Long, patch: InterviewEntity): Single<InterviewEntity> {
        interviewBox.put(patch)
        return Single.just(patch)
    }
}