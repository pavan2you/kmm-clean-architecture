package io.tagd.arch.test

import io.tagd.arch.scopable.Scopable
import io.tagd.di.Global
import io.tagd.di.Scope

class FakeScopable(
    override val outerScope: Scope = Global,
    override val thisScope: Scope = outerScope.addSubScopeIfAbsent("fake"),
    override val name: String = "fake"
) : Scopable {

    override fun release() {
        //no-op
    }
}

fun fakeScopable(
    outerScope: Scope = Global,
    thisScope: Scope = outerScope.addSubScopeIfAbsent("fake"),
    name: String = "fake"
): FakeScopable {

    return FakeScopable(outerScope, thisScope, name)
}