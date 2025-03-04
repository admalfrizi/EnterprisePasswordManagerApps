package api_testing.pass_data_in_group

import kotlinx.coroutines.test.runTest
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.utils.CamelliaCrypto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CryptoCameliaPassDataGroup {
    @Test
    fun `create pass data in group with encryption`() = runTest {
        val camelliaCrypto = CamelliaCrypto()
        val key = "pk45!@4695rqs@ty"
        val password = "lkqw23409ert"
        val encKey = camelliaCrypto.encrypt(password,key)

        val formPassDataGroup = PassDataGroupRequest(
            1,
            accountName =  "Akun Dinus",
            desc =  "ini adalah akun buat admin udinus",
            isEncrypted =  true,
            email = "erno@mhs.dinus.ac.id",
            jenisData = "Admin Udinus",
            password = encKey,
            url = "dinus.ac.id",
            username = "renodekorack",
            addPassContent = null
        )

        println("Hasil Enkripsi: $formPassDataGroup")
        assertNotEquals(password,formPassDataGroup.password)
    }

    @Test
    fun `create pass data in group decryption`() = runTest {
        val camelliaCrypto = CamelliaCrypto()
        val key = "pk45!@4695rqs@ty"
        val password = "lkqw23409ert"
        val encKey = camelliaCrypto.encrypt(password,key)
        val decData = camelliaCrypto.decrypt(encKey,key)

        val formPassDataGroup = PassDataGroupRequest(
            1,
            accountName =  "Akun Dinus",
            desc =  "ini adalah akun buat admin udinus",
            isEncrypted =  false,
            email = "erno@mhs.dinus.ac.id",
            jenisData = "Admin Udinus",
            password = decData,
            url = "dinus.ac.id",
            username = "renodekorack",
            addPassContent = null
        )

        println("Hasil Dekripsi: $formPassDataGroup")
        assertEquals(password,formPassDataGroup.password)
    }
}