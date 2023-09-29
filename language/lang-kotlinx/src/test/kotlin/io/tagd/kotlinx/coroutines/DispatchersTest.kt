package io.tagd.kotlinx.coroutines

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DispatchersTest {

    @Test(expected = IllegalAccessException::class)
    fun `given no provider is set then verify Dispatchers#get() throws exception`() {
        Dispatchers.get()
    }

    @Test
    fun `given no provider is set then verify Dispatchers#Main gets default Main`() {
        assert(Dispatchers.Main == kotlinx.coroutines.Dispatchers.Main)
    }

    @Test
    fun `given no provider is set then verify Dispatchers#Computation gets default Computation`() {
        assert(Dispatchers.Computation == kotlinx.coroutines.Dispatchers.Computation)
    }

    @Test
    fun `given no provider is set then verify Dispatchers#IO gets default IO`() {
        assert(Dispatchers.IO == kotlinx.coroutines.Dispatchers.IO)
    }

    @Test
    fun `given no provider is set then verify Dispatchers#Unconfined gets default Unconfined`() {
        assert(Dispatchers.Unconfined == kotlinx.coroutines.Dispatchers.Unconfined)
    }

    @Test
    fun `given provider is set then verify Dispatchers#get() returns the same`() {
        val provider = Dispatchers.Builder().build()
        Dispatchers.set(provider)
        assert(Dispatchers.get() == provider)
    }

    @Test
    fun `given provider is set Main then verify Dispatchers#Main returns the same`() {
        val provider = Dispatchers.Builder().Main(kotlinx.coroutines.Dispatchers.Main).build()
        Dispatchers.set(provider)

        assert(Dispatchers.Main == provider.Main)
    }

    @Test
    fun `given provider is set Computation then verify Dispatchers#Computation returns the same`() {
        val provider =
            Dispatchers.Builder().Computation(kotlinx.coroutines.Dispatchers.Computation).build()
        Dispatchers.set(provider)

        assert(Dispatchers.Computation == provider.Computation)
    }

    @Test
    fun `given provider is set then verify Dispatchers#IO returns the same`() {
        val provider = Dispatchers.Builder().IO(kotlinx.coroutines.Dispatchers.IO).build()
        Dispatchers.set(provider)

        assert(Dispatchers.IO == provider.IO)
    }

    @Test
    fun `given provider is set then verify Dispatchers#Unconfined returns the same`() {
        val provider =
            Dispatchers.Builder().Unconfined(kotlinx.coroutines.Dispatchers.Unconfined).build()
        Dispatchers.set(provider)

        assert(Dispatchers.Unconfined == provider.Unconfined)
    }

    @Test
    fun `given provider is set Main then verify provider#Main returns the same`() {
        val provider = Dispatchers.Builder().Main(kotlinx.coroutines.Dispatchers.Main).build()

        assert(kotlinx.coroutines.Dispatchers.Main == provider.Main)
    }

    @Test
    fun `given provider is set Computation then verify provider#Computation returns the same`() {
        val provider =
            Dispatchers.Builder().Computation(kotlinx.coroutines.Dispatchers.Computation).build()

        assert(kotlinx.coroutines.Dispatchers.Computation == provider.Computation)
    }

    @Test
    fun `given provider is set IO then verify provider#IO returns the same`() {
        val provider = Dispatchers.Builder().IO(kotlinx.coroutines.Dispatchers.IO).build()

        assert(kotlinx.coroutines.Dispatchers.IO == provider.IO)
    }

    @Test
    fun `given provider is set dao IO then verify provider#daoIO returns the same`() {
        val provider = Dispatchers.Builder().DaoIO(kotlinx.coroutines.Dispatchers.DaoIO).build()

        assert(kotlinx.coroutines.Dispatchers.DaoIO == provider.DaoIO)
    }

    @Test
    fun `given provider is set compute IO then verify provider#daoIO returns the same`() {
        val provider =
            Dispatchers.Builder().ComputeIO(kotlinx.coroutines.Dispatchers.ComputeIO).build()

        assert(kotlinx.coroutines.Dispatchers.ComputeIO == provider.ComputeIO)
    }

    @Test
    fun `given provider is set Unconfined then verify provider#Unconfined returns the same`() {
        val provider =
            Dispatchers.Builder().Unconfined(kotlinx.coroutines.Dispatchers.Unconfined).build()

        assert(kotlinx.coroutines.Dispatchers.Unconfined == provider.Unconfined)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given main dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.Main
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given io dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.IO
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given dao-io dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.DaoIO
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given compute-io dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.ComputeIO
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given unconfined dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.Unconfined
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `given default dispatcher is not set verify uninitialized exception thrown`() {
        val provider = Dispatchers.Builder().build()
        provider.Computation
    }
}