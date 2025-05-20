package com.arise.network.screens.details


import com.arise.network.models.PricingPackage

fun getDiamondPlatinumGoldPackage(): PricingPackage {
    return PricingPackage(
        name = "DIAMOND PLATINUM GOLD",
        price = "Ksh 4500",
        features = listOf(
            "Full Digital Strategy Management",
            "Dedicated Account Manager",
            "24/7 Support & Custom Reports",
            "All PRO features included"
        )
    )
}
