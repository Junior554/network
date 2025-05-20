package com.arise.network.screens.details


import com.arise.network.models.PricingPackage

fun getStarterPackage(): PricingPackage {
    return PricingPackage(
        name = "STARTER",
        price = "Ksh 1200",
        features = listOf(
            "Social Media Management (2 platforms)",
            "Basic SEO Optimization",
            "Monthly Performance Report"
        )
    )
}
