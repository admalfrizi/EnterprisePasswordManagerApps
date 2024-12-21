package data_sample

fun baseResponseSample(dataResponse: String) : String {
    val jsonContent = """{
        "success": true,
        "code": 200,
        "message": "Message Response",
        "data": $dataResponse
    }
    """.trimIndent()

    return jsonContent
}

fun baseErrorResponse(code: Int): String {
    val jsonContent = """{
        "success": false,
        "code": $code,
        "message": "Ini Adalah Kode Error $code",
        "data": null
    }
    """.trimIndent()

    return jsonContent
}
