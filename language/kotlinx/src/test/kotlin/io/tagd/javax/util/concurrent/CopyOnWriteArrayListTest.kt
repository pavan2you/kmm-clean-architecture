package io.tagd.javax.util.concurrent

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CopyOnWriteArrayList

@RunWith(MockitoJUnitRunner::class)
class CopyOnWriteArrayListTest {

    @Test
    fun `given removeAllByFilter is called then verify filtered results are removed`() {
        val collection = CopyOnWriteArrayList<String>()
        collection.add("one")
        collection.add("two")
        collection.add("three")
        collection.add("four")
        collection.add("five")

        io.tagd.langx.collection.concurrent.removeAllByFilter {
            it == "one" || it == "two"
        }

        assert(!collection.contains("one"))
        assert(!collection.contains("two"))
    }
}