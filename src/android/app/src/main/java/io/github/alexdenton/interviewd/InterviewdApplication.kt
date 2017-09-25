package io.github.alexdenton.interviewd

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidSupportFragmentScope
import io.github.alexdenton.interviewd.api.*

/**
 * Created by ryan on 8/24/17.
 */
class InterviewdApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<QuestionRepository>() with instance(QuestionRetrofitRepository(applicationContext))
        bind<TemplateRepository>() with instance(TemplateRetrofitRepository(applicationContext))
        bind<CandidateRepository>() with instance(CandidateRetrofitRepository(applicationContext))
        bind<InterviewRepository>() with instance(InterviewRetrofitRepository(applicationContext))
    }
}