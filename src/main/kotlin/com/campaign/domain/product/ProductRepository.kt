package com.campaign.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>, ProductCustomRepository {
    fun findAllByIdInAndDeactivatedAtIsNull(productIdList: List<Long>): List<Product>
}
