package com.campaign.controller

data class ErrorResponse(
    override val status: Int,
    override val message: String?,
) : DefaultResponse()
