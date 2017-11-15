package io.github.alexdenton.interviewd.objectbox.repositories

import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.entities.Question
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.reactivex.Single

/**
 * Created by ryan on 11/14/17.
 */
class QuestionObjectboxRepository(val client: InterviewdObjectboxApi) : QuestionRepository {

    override fun getQuestion(id: Long): Single<Question> {
        return client.getQuestion(id).map { it.toQuestion() }
    }

    override fun getAllQuestions(): Single<List<Question>> {
        return client.getQuestions().map { it.map { it.toQuestion() } }
    }

    override fun createQuestion(question: Question): Single<Question> {
        return client.createQuestion(question.toEntity()).map { it.toQuestion() }
    }

    override fun updateQuestion(question: Question): Single<Question> {
        return client.patchQuestion(question.id, question.toEntity()).map { it.toQuestion() }
    }

    override fun deleteQuestion(id: Long): Single<Question> {
        return client.deleteQuestion(id).map { it.toQuestion() }
    }


}