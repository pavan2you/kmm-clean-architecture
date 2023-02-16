package io.tagd.javax.util

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractMapTest {

    @Test
    fun `given when getKeys is called then verify it will return a list of keys from map`() {
        val map = HashMap<String, String>()
        map["one"] = "ONE"
        map["two"] = "TWO"
        val list = map.getKeys()
        assert(list.contains("one"))
        assert(list.contains("two"))
    }
}