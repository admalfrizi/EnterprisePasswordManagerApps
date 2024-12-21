package cryptography_test

import org.apps.simpenpass.utils.CamelliaCrypto
import kotlin.test.Test
import kotlin.test.assertNotSame

class DecryptionTest {
    private val camelliaCrypto = CamelliaCrypto()
    private val data = "Adam Alfarizi Ismail"

    @Test
    fun `decryption test with key 128 bit`() {
        val key = "pk45!@4695rqs@ty"

        val encData = camelliaCrypto.encrypt(data,key)
        val decData = camelliaCrypto.decrypt(encData,key)

        println("Hasil Enkripsi 128 bit : $encData")
        println("Hasil Dekripsi 128 bit : $decData")

        assertNotSame(data,encData)
    }

    @Test
    fun `decryption test with key 192 bit`() {
        val key = "BiggerIsNotAlwaysBetter"

        val encData = camelliaCrypto.encrypt(data,key)
        val decData = camelliaCrypto.decrypt(encData,key)

        println("Hasil Enkripsi 192 bit : $encData")
        println("Hasil Dekripsi 192 bit : $decData")

        assertNotSame(data,encData)
    }

    @Test
    fun `decryption test with key 256 bit`() {
        val key = "BiggerIsNotAlwaysBetterOkayWhy"

        val encData = camelliaCrypto.encrypt(data,key)
        val decData = camelliaCrypto.decrypt(encData,key)

        println("Hasil Enkripsi 256 bit : $encData")
        println("Hasil Dekripsi 256 bit : $decData")

        assertNotSame(data,decData)
    }
}