package com.example.wfrleytask.model


data class Item(
    val cashierComment: Any? ="",
    val cashierUser: Any? ="",
    val couponCode: String? ="",
    val createdDate: String? ="",
    val customerServiceComment: Any? ="",
    val customerServiceUser: CustomerServiceUser?,
    val customerUser: CustomerUser?,
    val deliveryUser: Any?,
    val discountAmount: Int?,
    val fromPos: Boolean?,
    val grandTotal: Double?,
    val id: Int,
    val idOnline: Any?,
    val inventories: Inventories?,
    val merchantId: String?,
    val operationNumber: Any?,
    val orderNumber: String?,
    val paymentMethod: Int?,
    val postponedDate: String?,
    val preparingOrderEmployeeUser: Any?,
    val priceAfterDiscountTotal: Double?,
    val purchasePoint: Int?,
    val serviceFee: Double?,
    val shippingAddresses: List<ShippingAddresse>?,
    val shippingAmount: Double?,
    val shippingMethod: Int?,
    val startCompleteDate: Any?,
    val startShippingDate: Any?,
    val status: Int?,
    val syncSucceed: Boolean?,
    val totalRefunded: Int?,
    val updatedDate: String?
)

fun Item.toOrderEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        date = createdDate ?: "",
        price = grandTotal ?: 0.0,
        clientName = customerUser?.displayName ?: ""
    )

}
