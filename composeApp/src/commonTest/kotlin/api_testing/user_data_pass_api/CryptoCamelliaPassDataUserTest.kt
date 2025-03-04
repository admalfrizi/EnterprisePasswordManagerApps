package api_testing.user_data_pass_api

import kotlinx.coroutines.test.runTest
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.utils.CamelliaCrypto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CryptoCamelliaPassDataUserTest {
    @Test
    fun `create pass data user with encryption api success`() = runTest {
        val camelliaCrypto = CamelliaCrypto()
        val key = "pk45!@4695rqs@ty"
        val password = "lkqw23409ert"
        val encKey = camelliaCrypto.encrypt(password,key)
        val formPassData = PassDataRequest(
            "Akun Dinus",
            "ini adalah akun buat admin udinus",
            true,
            "erno@mhs.dinus.ac.id",
            "Admin Udinus",
            encKey,
            "dinus.ac.id",
            "renodekorack"
        )

        println("Hasil Enkripsi: $formPassData")
        assertNotEquals(password,formPassData.password)
    }

    @Test
    fun `create pass data user with decryption`() = runTest {
        val camelliaCrypto = CamelliaCrypto()
        val key = "pk45!@4695rqs@ty"
        val password = "lkqw23409ert"
        val encKey = camelliaCrypto.encrypt(password,key)
        val decData = camelliaCrypto.decrypt(encKey,key)

        val formPassData = PassDataRequest(
            "Akun Dinus",
            "ini adalah akun buat admin udinus",
            false,
            "erno@mhs.dinus.ac.id",
            "Admin Udinus",
            decData,
            "dinus.ac.id",
            "renodekorack"
        )

        println("Hasil Dekripsi: $formPassData")
        assertEquals(password,formPassData.password)
    }
}