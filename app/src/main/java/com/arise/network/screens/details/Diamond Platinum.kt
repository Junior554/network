package com.arise.network.screens.details


import com.arise.network.models.PricingPackage

fun getDiamondPlatinumPackage(): PricingPackage {
    return PricingPackage(
        name = "DIAMOND PLATINUM",
        price = "Ksh 3500",
        features = listOf(
            "Full Digital Strategy Management",
            "Dedicated Account Manager",
            "24/7 Support & Custom Reports"
        )
    )
}
