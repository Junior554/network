package com.arise.network.screens.details


import com.arise.network.models.PricingPackage

fun getSilverPackage(): PricingPackage {
    return PricingPackage(
        name = "SILVER",
        price = "Ksh 1500",
        features = listOf(
            "Social Media Management (4 platforms)",
            "Advanced SEO Optimization",
            "Email Marketing (2 campaigns/month)",
            "Monthly Report with Analytics"
        )
    )
}
