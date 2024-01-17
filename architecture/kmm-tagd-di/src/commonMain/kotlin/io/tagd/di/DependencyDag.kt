package io.tagd.di

import io.tagd.core.Service
import io.tagd.langx.collection.concurrent.ConcurrentHashMap

class DependencyDag {

    private val dag = ConcurrentHashMap<Key<out Service>, DependentsWithInfluencerScope>()

    fun <T : DependableService> put(
        influencer: Key<out Service>,
        influencerScope: String,
        dependent: T
    ) {

        var dependents = dag[influencer]?.get(influencerScope)
        if (dependents == null) {
            dependents = arrayListOf()
            dag[influencer] = DependentsWithInfluencerScope().apply {
                    put(influencerScope, dependents)
                }
        }
        if (!dependents.contains(dependent)) {
            dependents.add(dependent)
        }
    }

    fun containsKey(influencer: Key<out Service>): Boolean {
        return dag.containsKey(influencer)
    }

    operator fun get(
        influencer: Key<out Service>
    ): DependentsWithInfluencerScope? {

        return dag[influencer]
    }

    fun remove(influencer: Key<out Service>, influencerScope: String) {
        val map = dag[influencer]
        map?.let {
            it[influencerScope]?.clear()
            dag.remove(influencer)
        }
    }

    fun isEmpty(): Boolean {
        return dag.isEmpty()
    }

    fun clear() {
        dag.values.forEach { map ->
            map.clear()
        }
        dag.clear()
    }

    class DependentsWithInfluencerScope {

        private val map = ConcurrentHashMap<String, ArrayList<DependableService>>()

        operator fun get(
            key: String
        ): ArrayList<DependableService>? {

            return map[key]
        }

        fun put(influencerScope: String, dependents: ArrayList<DependableService>) {
            map[influencerScope] = dependents
        }

        inline operator fun set(influencerScope: String, dependents: ArrayList<DependableService>) {
            put(influencerScope, dependents)
        }

        fun clear() {
            map.values.forEach { list ->
                list.clear()
            }
            map.clear()
        }
    }
}