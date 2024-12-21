package cryptography_test

import org.apps.simpenpass.utils.CamelliaCrypto
import kotlin.test.Test
import kotlin.test.assertNotSame

class EncryptionTest {
    private val camelliaCrypto = CamelliaCrypto()
    private val data = "Adam Alfarizi Ismail"

    @Test
    fun `encryption test with key 128 bit`() {
        val key = "pk45!@4695rqs@ty"

        val encKey = camelliaCrypto.encrypt(data,key)
        println("Hasil Enkripsi 128 bit : $encKey")

        assertNotSame(data,encKey)
    }

    @Test
    fun `encryption test with key 192 bit`() {
        val key = "BiggerIsNotAlwaysBetter"

        val encKey = camelliaCrypto.encrypt(data,key)
        println("Hasil Enkripsi 192 bit : $encKey")

        assertNotSame(data,encKey)
    }

    @Test
    fun `encryption test with key 256 bit`() {
        val key = "BiggerIsNotAlwaysBetterOkayWhy"

        val encKey = camelliaCrypto.encrypt(data,key)
        println("Hasil Enkripsi 256 bit : $encKey")

        assertNotSame(data,encKey)
    }
}