package io.tagd.di

interface BidirectionalDependentOn<T> {

    fun injectBidirectionalDependent(other: T)
}