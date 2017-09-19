package io.github.alexdenton.interviewd.createinterview

import io.github.alexdenton.interviewd.api.CandidateRepository
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.api.TemplateRepository
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/15/17.
 */
class CreateInterviewPresenter(val templateRepo: TemplateRepository, val questionRepo: QuestionRepository, val candidateRepo: CandidateRepository, val activity: CreateInterviewActivity) {

    val questions = mutableListOf<Question>()


    fun getTemplates() = templateRepo.getAllTemplates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({list -> activity.setupTemplateDialog(list)},
                    {throwable -> throwable.printStackTrace()})

    fun getQuestions() = questionRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({list -> activity.setupQuestionDialog(list)},
                    {throwable -> throwable.printStackTrace()})

}