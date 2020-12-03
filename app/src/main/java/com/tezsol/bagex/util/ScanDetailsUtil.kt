package com.tezsol.bagex.util

import com.tezsol.bagex.model.response.OrdersInfo
import java.util.*

class ScanDetailsUtil {

    private val scanListItems: MutableList<OrdersInfo> = ArrayList()

    private object HOLDER {
        val INSTANCE = ScanDetailsUtil()
    }

    companion object {
        val instance: ScanDetailsUtil by lazy { HOLDER.INSTANCE }
    }

    fun addOrderInfo(ordersInfo: OrdersInfo) {
        scanListItems.add(ordersInfo)
    }

    fun removeOrderInfo(ordersInfo: OrdersInfo) {
        scanListItems.remove(ordersInfo)
    }

    fun clearOrderInfo() {
        scanListItems.clear()
    }

    fun getOrderInfo(): MutableList<OrdersInfo> {
        return scanListItems
    }

    fun isContain(barcode: String): Boolean {
        scanListItems.forEach { ordersInfo -> if (ordersInfo.bagId.equals(barcode)) return true }
        return false
    }
}