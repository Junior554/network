package com.arise.network.models


data class PricingPackage(
    val name: String,
    val price: String,
    val features: List<String>
)

val pricingPackages = mapOf(
    "STARTER" to PricingPackage(
        name = "STARTER",
        price = "Ksh 1200",
        features = listOf(
            "Social Media Management (2 platforms)",
            "Basic SEO Optimization",
            "Monthly Performance Report"
        )
    ),

    "GOLD" to PricingPackage(
        name = "GOLD",
    price="Ksh 2500",
    features =listOf(
            "Unlimited Social Media Management",
            "Comprehensive SEO Strategy",
            "Email Marketing (4 campaigns/month)",
            "PPC & Advertising Management",
            "Detailed Monthly Report"
        )
    ),
    "DIAMOND PLATINUM" to PricingPackage(
        name="DIAMOND PLATINUM",
        price = "Ksh 3500",
        features = listOf(
            "Full Digital Strategy Management",
            "Dedicated Account Manager",
            "24/7 Support & Custom Reports"
        )
    ),
    "DIAMOND PLATINUM GOLD" to PricingPackage(
        name = "DIAMOND PLATINUM GOLD",
        price = "Ksh 4500",
        features = listOf(
            "Full Digital Strategy Management",
            "Dedicated Account Manager",
            "24/7 Support & Custom Reports",
            "All PRO features included"
        )
    )
)


