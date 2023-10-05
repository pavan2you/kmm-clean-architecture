package io.tagd.langx.ref

class ComparableWeakReference<T>(referent: T) : WeakReference<T>(referent) {

    override fun equals(other: Any?): Boolean {
        return if (other is WeakReference<*>) {
            get() === other.get()
        } else {
            get() === other
        }
    }

    override fun hashCode(): Int {
        return get().hashCode()
    }
}