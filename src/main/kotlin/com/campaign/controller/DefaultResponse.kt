package com.campaign.controller

interface Response {
    val status: Int
    val message: String?
}

open class DefaultResponse(
    override val status: Int = HttpResponseStatus.OK.statusCode,
    override val message: String? = null,
) : Response {
    companion object {
        fun ok(): DefaultResponse = DefaultResponse()
    }
}

enum class HttpResponseStatus(
    val statusCode: Int,
    val message: String,
) {
    OK(0, "OK"),
    INVALID_REQUEST(400, "유효하지 않는 요청입니다"),
    METHOD_NOT_ALLOWED(405, "지원하지 않는 HTTP 메소드 입니다"),
    NOT_FOUND(404, "리소스가 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR(500, "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요"),
}
