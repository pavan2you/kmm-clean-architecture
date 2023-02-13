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
    fun `given no provider is set then verify Dispatchers#Default gets default Default`() {
        assert(Dispatchers.Default == kotlinx.coroutines.Dispatchers.Default)
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
    fun `given provider is set Default then verify Dispatchers#Default returns the same`() {
        val provider = Dispatchers.Builder().Default(kotlinx.coroutines.Dispatchers.Default).build()
        Dispatchers.set(provider)

        assert(Dispatchers.Default == provider.Default)
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
    fun `given provider is set Default then verify provider#Default returns the same`() {
        val provider = Dispatchers.Builder().Default(kotlinx.coroutines.Dispatchers.Default).build()

        assert(kotlinx.coroutines.Dispatchers.Default == provider.Default)
    }

    @Test
    fun `given provider is set IO then verify provider#IO returns the same`() {
        val provider = Dispatchers.Builder().IO(kotlinx.coroutines.Dispatchers.IO).build()

        assert(kotlinx.coroutines.Dispatchers.IO == provider.IO)
    }

    @Test
    fun `given provider is set Unconfined then verify provider#Unconfined returns the same`() {
        val provider =
            Dispatchers.Builder().Unconfined(kotlinx.coroutines.Dispatchers.Unconfined).build()

        assert(kotlinx.coroutines.Dispatchers.Unconfined == provider.Unconfined)
    }
}