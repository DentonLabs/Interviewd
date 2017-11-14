package io.github.alexdenton.interviewd.di

import android.content.Context
import com.github.salomonbrys.kodein.*
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.api.repositories.InterviewRepository
import io.github.alexdenton.interviewd.api.repositories.QuestionRepository
import io.github.alexdenton.interviewd.api.repositories.TemplateRepository
import io.github.alexdenton.interviewd.objectbox.InterviewdObjectboxApi
import io.github.alexdenton.interviewd.objectbox.ObjectBoxApi
import io.github.alexdenton.interviewd.objectbox.dto.MyObjectBox
import io.github.alexdenton.interviewd.objectbox.repositories.CandidateObjectboxRepository
import io.github.alexdenton.interviewd.objectbox.repositories.InterviewObjectboxRepository
import io.github.alexdenton.interviewd.objectbox.repositories.QuestionObjectboxRepository
import io.github.alexdenton.interviewd.objectbox.repositories.TemplateObjectboxRepository
import io.objectbox.BoxStore

/**
 * Created by ryan on 11/14/17.
 */
fun getApiModule(context: Context) = Kodein.Module {

    bind<BoxStore>() with singleton { MyObjectBox.builder().androidContext(context).build() }
    bind<InterviewdObjectboxApi>() with provider { ObjectBoxApi(instance()) }
    bind<QuestionRepository>() with provider { QuestionObjectboxRepository(instance()) }
    bind<TemplateRepository>() with provider { TemplateObjectboxRepository(instance()) }
    bind<CandidateRepository>() with provider { CandidateObjectboxRepository(instance()) }
    bind<InterviewRepository>() with provider { InterviewObjectboxRepository(instance()) }
}
