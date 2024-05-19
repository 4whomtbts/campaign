# 구현범위

**1.카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API**

API: GET /api/v1/products/min-price/all-categories/by-price


**2.단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API**

API: GET /api/v1/products/min-total-price/all-categories/by-brand

**3.카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API**

API: GET /api/v1/products/min-total-price/min-max-price/categories

**4. 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API**

브랜드 추가 API: POST /operation/v1/brands

브랜드 업데이트 API: PUT /operation/v1/brands

브랜드 삭제 API: DELETE /operation/v1/brands

상품 추가 API: POST /operation/v1/products

상품 업데이트 API: PUT /operation/v1/products

상품 삭제 API: DELETE /operation/v1/products

# 빌드 및 테스트 방법

빌드 및 테스트: ./gradlew build

실행: ./gradlew bootRun --args='--spring.profiles.active=sandbox'

테스트: ./gradlew test

# API 테스트 방법
각 API 별로 호출 해볼 수 있는 .http 파일을 제공


src/main/resources/http
- product.http: 구현 (1), (2), (3)
- etc.http: HTTP STATUS 404 등의 응답 포맷 확인
- /operation/*.http: 구현(4)