package com.campaign.domain.brand

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor

class BrandRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : BrandCustomRepository
