@file:Suppress("unused")

package io.tagd.arch.scopable

import io.tagd.core.Nameable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Key
import io.tagd.di.Layer
import io.tagd.di.Scope
import io.tagd.di.bind
import io.tagd.di.bindLazy
import io.tagd.di.create
import io.tagd.di.get
import io.tagd.di.getLazy
import io.tagd.di.getWithScope
import io.tagd.di.inject
import io.tagd.di.layer

/**
 * A [Scopable] is something equal to a sandbox / virtual container. Typical examples of Scopable
 * are - Process, Application, some application module, some library, some component like
 * Activity/Service/BroadCastReceiver/ViewController etc.
 */
interface Scopable : Service, Nameable {

    val outerScope: Scope

    val thisScope: Scope
}

fun Scopable.inject(bindings: Scope.() -> Unit): Scopable {
    thisScope.inject(bindings)
    return this
}

inline fun <reified T : Service> Scopable.layer(bindings: Layer<T>.() -> Unit): Layer<T> {
    return thisScope.locator.layer(bindings)
}

fun <T : Service, S : T> Scopable.get(clazz: Key<S>): S? {
    return thisScope.get(clazz)
}

fun <T : Service, S : T> Scopable.getWithScope(clazz: Key<S>): Pair<Scope?, S?> {
    return thisScope.getWithScope(clazz)
}

fun <T : Service, S : T> Scopable.create(key: Key<S>, args: State? = null): S {
    return thisScope.create(key, args)
}

fun <T : Service, S : T> Scopable.getLazy(key: Key<S>, args: State? = null): S {
    return thisScope.getLazy(key, args)
}

inline fun <reified T : Service, reified S : T> Scopable.bind(key: Key<S>? = null, instance: S) {
    thisScope.bind(key, instance)
}

inline fun <reified T : Service, reified S : T> Scopable.bindLazy(
    key: Key<S>? = null,
    noinline creator: (State?) -> S
) {

    thisScope.bindLazy(key, creator)
}