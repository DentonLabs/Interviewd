package io.github.alexdenton.interviewd.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.retrofit.repositories.CandidateRetrofitRepository
import io.github.alexdenton.interviewd.retrofit.repositories.InterviewRetrofitRepository
import io.github.alexdenton.interviewd.retrofit.repositories.QuestionRetrofitRepository
import io.github.alexdenton.interviewd.retrofit.repositories.TemplateRetrofitRepository

/**
 * Created by ryan on 11/14/17.
 */

fun getApiModule(context: Context) = Kodein.Module {
    bind<QuestionRepository>() with instance(QuestionRetrofitRepository(context))
    bind<TemplateRepository>() with instance(TemplateRetrofitRepository(context))
    bind<CandidateRepository>() with instance(CandidateRetrofitRepository(context))
    bind<InterviewRepository>() with instance(InterviewRetrofitRepository(context))
}