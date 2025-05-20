package com.arise.network.screens.details


import com.arise.network.models.PricingPackage

fun getGoldPackage(): PricingPackage {
    return PricingPackage(
        name = "GOLD",
        price = "Ksh 2500",
        features = listOf(
            "Unlimited Social Media Management",
            "Comprehensive SEO Strategy",
            "Email Marketing (4 campaigns/month)",
            "PPC & Advertising Management",
            "Detailed Monthly Report"
        )
    )
}
