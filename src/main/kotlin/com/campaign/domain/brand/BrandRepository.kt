package com.campaign.domain.brand

import com.campaign.domain.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long>, BrandCustomRepository {
    fun findByIdAndDeactivatedAtIsNull(id: Long): Brand?
    fun findByIdInAndDeactivatedAtIsNull(ids: List<Long>): List<Brand>
}