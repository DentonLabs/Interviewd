package io.github.alexdenton.interviewd

import android.app.Application
import com.github.salomonbrys.kodein.*
import io.github.alexdenton.interviewd.di.getApiModule

/**
 * Created by ryan on 8/24/17.
 */
class InterviewdApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(getApiModule(applicationContext))
    }
}