package com.campaign.domain.product

@JvmInline
value class ProductPrice(
    val value: Long,
) {
    init {
        if (value < 0) {
            throw IllegalArgumentException("상품의 가격은 0보다 작을 수 없습니다")
        }
    }
}
