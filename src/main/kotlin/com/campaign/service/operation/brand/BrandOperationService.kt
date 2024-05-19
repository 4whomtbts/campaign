package com.campaign.service.operation.brand

import com.campaign.domain.Brand
import com.campaign.domain.brand.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BrandOperationService(
    private val brandRepository: BrandRepository,
) {
    @Transactional
    fun createBrands(brandNames: List<String>) {
        brandNames.forEach {
            brandRepository.save(Brand(brandName = it))
        }
    }

    @Transactional
    fun updateBrands(brandUpdates: List<BrandUpdate>) {
        val brandMap = brandUpdates.groupBy(BrandUpdate::brandId)
        val brandEntities =
            brandRepository.findByIdInAndDeactivatedAtIsNull(brandUpdates.map(BrandUpdate::brandId))

        brandEntities.forEach {
            val brandUpdate =
                brandMap[it.id] ?: throw IllegalArgumentException("존재하지 않는 브랜드 입니다. brandId = ${it.id}")
            if (brandUpdate.size > 1) {
                throw IllegalArgumentException("동일한 브랜드에 대해서 복수의 업데이트 요청을 할 수 없습니다. brandId = ${it.id}")
            }
            val update = brandUpdate.first()
            if (!update.brandName.isNullOrBlank()) {
                it.brandName = update.brandName
            }
        }
    }

    @Transactional
    fun deleteBrands(brandDeletes: List<BrandDelete>) {
        val deleteTargetIds = brandDeletes.map(BrandDelete::brandId)
        val brandEntities = brandRepository.findByIdInAndDeactivatedAtIsNull(deleteTargetIds)

        brandEntities.forEach {
            it.deactivatedAt = LocalDateTime.now()
        }
    }

    data class BrandUpdate(
        val brandId: Long,
        val brandName: String?,
    )

    data class BrandDelete(
        val brandId: Long,
    )
}
