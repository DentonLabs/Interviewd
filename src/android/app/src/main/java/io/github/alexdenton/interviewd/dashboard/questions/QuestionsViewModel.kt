package io.github.alexdenton.interviewd.dashboard.questions

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.api.QuestionRepository
import io.github.alexdenton.interviewd.createquestion.CreateQuestionActivity
import io.github.alexdenton.interviewd.detailquestion.QuestionDetailActivity
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/25/17.
 */
class QuestionsViewModel : RxAwareViewModel() {

    lateinit var questionsRepo: QuestionRepository

    private val questions: PublishRelay<List<Question>> = PublishRelay.create()

    fun getQuestionsObservable(): Observable<List<Question>> = questions

    fun initWith(kodein: LazyKodein) {
        questionsRepo = kodein.invoke().instance()
        fetchQuestions()
    }

    private fun fetchQuestions() = questionsRepo.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list -> questions.accept(list) },
                    { throwable -> throwable.printStackTrace() })
            .lifecycleAware()

    fun exposeAddFab(clicks: Observable<Unit>) = clicks
            .subscribe { startActivity(CreateQuestionActivity::class.java) }

    fun exposeItemClicks(itemClicks: Observable<Question>) = itemClicks
            .subscribe { startActivityAndStore(QuestionDetailActivity::class.java, it.id) }

}