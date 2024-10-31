package com.example.wfrleytask.model

data class OrderDetail(
    var id: Int,
    var product: Product,
    var productCustomizationId: Any,
    var productCustomizations: Any,
    var quantity: Double,
    var rowPriceAfterDiscount: Double,
    var rowTotal: Double,
    var syncSucceed: Boolean
)

fun OrderDetail.toOrderDetailRequest(): OrderDetailRequest {
    return OrderDetailRequest(
        productId = id,
        quantity = quantity,
        rowPriceAfterDiscount = rowPriceAfterDiscount,
        rowTotal = rowTotal)
}
