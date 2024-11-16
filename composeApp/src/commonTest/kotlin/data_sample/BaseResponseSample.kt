package data_sample

fun baseReponseSample(dataResponse: String) : String {
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
        "message": "Message Error",
        "data": null
    }
    """.trimIndent()

    return jsonContent
}
