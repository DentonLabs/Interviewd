package io.github.alexdenton.interviewd.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.github.alexdenton.interviewd.question.Question
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by ryan on 8/24/17.
 */
object RxBus {
    private val subject: BehaviorRelay<Any> = BehaviorRelay.create()

    fun post(event: Any) = subject.accept(event)

    fun <T> getEvents(eventType: Class<T>): Observable<T> = subject.ofType(eventType)
}


//val questionBankRelayModule = Kodein.Module {
//    bind<RxBus>() with singleton { RxBus() }
//}