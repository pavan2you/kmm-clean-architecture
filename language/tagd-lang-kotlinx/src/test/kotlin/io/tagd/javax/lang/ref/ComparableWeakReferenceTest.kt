package io.tagd.javax.lang.ref

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ComparableWeakReferenceTest {

    @Test
    fun `given two CWRs with same referent then verify they are equal`() {
        val referent = "hello"
        val cwr1 = ComparableWeakReference(referent)
        val cwr2 = ComparableWeakReference(referent)
        assert(cwr1 == cwr2)
        assert(cwr1.hashCode() == cwr2.hashCode())
    }

    @Test
    fun `given two CWRs with same referent then verify they are equal by hashcode`() {
        val referent = "hello"
        val cwr1 = ComparableWeakReference(referent)
        val cwr2 = ComparableWeakReference(referent)
        assert(cwr1.hashCode() == cwr2.hashCode())
    }

    @Test
    fun `given two CWRs with different referents then verify they are not equal`() {
        val referent1 = "hello"
        val referent2 = "HELLO"
        val cwr1 = ComparableWeakReference(referent1)
        val cwr2 = ComparableWeakReference(referent2)
        assert(cwr1 != cwr2)
    }

    @Test
    fun `given two CWRs with different referents then verify their hashcodes are not equal`() {
        val referent1 = "hello"
        val referent2 = "HELLO"
        val cwr1 = ComparableWeakReference(referent1)
        val cwr2 = ComparableWeakReference(referent2)
        assert(cwr1.hashCode() != cwr2.hashCode())
    }

    @Test
    fun `given two CWRs with different referents and one of them is null referent then verify they are not equal`() {
        val referent1 = "hello"
        val referent2 = null
        val cwr1 = ComparableWeakReference(referent1)
        val cwr2 = ComparableWeakReference(referent2)
        assert(cwr1 != cwr2)
    }

    @Test
    fun `given two CWRs with different referents and one of them is null referent then verify their hashcodes are not equal`() {
        val referent1 = "hello"
        val referent2 = null
        val cwr1 = ComparableWeakReference(referent1)
        val cwr2 = ComparableWeakReference(referent2)
        assert(cwr1.hashCode() != cwr2.hashCode())
    }

    @Test
    fun `given two CWRs with same referent but the 2nd one is a WR of referent then verify they are equal`() {
        val referent = "hello"
        val cwr1 = ComparableWeakReference(referent)
        val cwr2 = ComparableWeakReference(ComparableWeakReference(referent))
        assert(cwr1 == cwr2)
        assert(cwr1.hashCode() == cwr2.hashCode())
    }
}