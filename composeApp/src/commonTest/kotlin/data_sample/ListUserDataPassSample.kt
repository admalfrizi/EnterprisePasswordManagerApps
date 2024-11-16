package data_sample

class ListUserDataPassSample {

    val listUserPassData = """[
        {
            "id": 1,
            "jenis_data": "Google",
            "account_name": "Akun Google",
            "username": "adamal26",
            "password": "poiuytre",
            "email": "adam.alfarizi.2002@gmail.com",
            "url": "uil.com",
            "desc": "owfowefbewofbewo",
            "add_pass_content": [
                {
                    "id": 1,
                    "pass_id": 1,
                    "nm_data": "foewhfoewf",
                    "vl_data": "fwofhwoufwef"
                },
                {
                    "id": 2,
                    "pass_id": 1,
                    "nm_data": "foewhfoewfggerger",
                    "vl_data": "fwofhwou"
                }
            ]
        }
    ]
    """.trimIndent()

    val listUserPassDataWithEmptyAddContent = """[
        {
            "id": 1,
            "jenis_data": "Google",
            "account_name": "Akun Google",
            "username": "adamal26",
            "password": "poiuytre",
            "email": "adam.alfarizi.2002@gmail.com",
            "url": "uil.com",
            "desc": "owfowefbewofbewo",
            "add_pass_content": []
        }
    ]
    """.trimIndent()
}