package data_sample

class GroupDetailsSample {
    val detailGroupData = """
        {
            "group_dtl": {
                "id": 7,
                "img_grup": "http://simpenpass-api.pro/storage/images/groupProfile/1732813151.jpg",
                "nm_grup": "Afk Group Pass",
                "desc": "wdakkdbakdbawldawbda"
            },
            "is_user_admin": true,
            "is_group_secure": false,
            "is_pass_encrypted": false,
            "total_pass_data": 1,
            "total_role": 2,
            "total_member": 2
        }
    """.trimIndent()

    val listPassDataGroup = """
        [
            {
                "id": 1,
                "klmpk_role": "Ekonomi",
                "nama_grup": "Huliya Go",
                "account_name": "xtesr",
                "username": "fzfszsf",
                "email": "cfcgcesr",
                "password": "vyftrsrwawfzf",
                "url": "fcgdsfwaewr",
                "desc": "butfyrdtrdtesr"
            }
        ]
    """.trimIndent()

    val passDataGroupId = """
        {
            "id": 3,
            "posisi_id": 3,
            "klmpk_role": "Dosen Sekretariat",
            "account_name": "Test Enkripsid",
            "username": "awfawfesfsh",
            "email": "fesgsrhseh",
            "password": "bnngmgwewfdsgfsdgs",
            "jenis_data": "rdhdrhrdhd",
            "url": "sefsegsehs",
            "desc": "gsgsehshse",
            "is_encrypted": false,
            "add_pass_content": [
                {
                    "id": 15,
                    "pass_group_id": 3,
                    "nm_data": "yjytktukub",
                    "vl_data": "ddhdrhdhr"
                }
            ]
        }
    """.trimIndent()
}