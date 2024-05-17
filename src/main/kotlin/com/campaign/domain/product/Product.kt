package com.campaign.domain.product

import com.campaign.domain.Brand
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kotlin.IllegalArgumentException

@Entity
class Product(
    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val brand: Brand,

    @Column(name = "price")
    var price: Long,

    @Column(name = "category", nullable = false, columnDefinition = "integer")
    @Convert(converter = ProductCategoryAttributeConverter::class)
    var category: ProductCategory,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    enum class ProductCategory(
        val code: Int,
        val categoryName: String,
    ) {
        TOP(0, "상의"),
        OUTWEAR(1, "아우터"),
        PANTS(2, "바지"),
        SNEAKERS(3, "스니커즈"),
        BAG(4, "가방"),
        HAT(5, "모자"),
        SOCKS(6, "양말"),
        ACCESSORIES(7, "액세서리"),
        ;

        companion object {
            fun ofCode(code: Int): ProductCategory =
                values().find { it.code == code } ?: throw IllegalArgumentException("존재하지 않는 코드의 카테고리입니다.")
        }
    }

    @Converter(autoApply = true)
    class ProductCategoryAttributeConverter : AttributeConverter<ProductCategory, Int> {
        override fun convertToDatabaseColumn(attribute: ProductCategory): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): ProductCategory {
            return ProductCategory.values().find { it.code == dbData }
                ?: throw IllegalArgumentException("존재하지 않는 상품 카테고리 코드입니다. code = $dbData")
        }
    }
}
