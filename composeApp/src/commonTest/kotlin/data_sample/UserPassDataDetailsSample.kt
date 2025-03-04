package data_sample

import org.apps.simpenpass.models.request.PassDataRequest

class UserPassDataDetailsSample {
    val detailsUserPassData = """{
        "id": 27,
        "user_id": 3,
        "jenis_data": "Admin Web",
        "account_name": "Sister Udinus",
        "username": "dwadawd",
        "password": "yr5ye5ydwar3qr",
        "email": "ert9ierpt9uwdq",
        "url": "fsefnseregerge",
        "desc": "y54y443t34y",
        "is_encrypted": false
    }
    """.trimIndent()

    val createPassData = PassDataRequest(
        "Akun Dinus",
        "ini adalah akun buat admin udinus",
         false,
         "erno@mhs.dinus.ac.id",
         "Admin Udinus",
         "poiklmnjuhb",
        "dinus.ac.id",
        "renodekorack"
    )
}