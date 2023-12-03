package com.dooho.board.api

data class ResponseDto<D>(val isResult: Boolean, val message: String, val data: D) {
    companion object {
        @JvmStatic
        fun <D> setSuccess(message: String, data: D): ResponseDto<D> {
            return ResponseDto(true, message, data)
        }

        @JvmStatic
        fun <D> setFailed(message: String): ResponseDto<D?> {
            return ResponseDto(false, message, null)
        }
    }
}
