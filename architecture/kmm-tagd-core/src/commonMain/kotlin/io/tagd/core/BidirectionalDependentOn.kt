package io.tagd.core

interface BidirectionalDependentOn<T> {

    fun injectBidirectionalDependent(other: T)
}