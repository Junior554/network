package com.arise.network.screens.details

data class All(
    val name: String,
    val price: String,
    val features: List<String>
)

val AllPackagesMap = mapOf(
    "STARTER" to All(
        name = "STARTER",
        price = "Ksh 1200",
        features = listOf(
            "Social Media Management (2 platforms)",
            "Basic SEO Optimization",
            "Monthly Performance Report"
        )
    ),
    "SILVER" to All(
        name = "SILVER",
        price = "Ksh 1500",
        features = listOf(
            "Social Media Management (4 platforms)",
            "Advanced SEO Optimization",
            "Email Marketing (2 campaigns/month)",
            "Monthly Report with Analytics"
        )
    ),
    "GOLD" to All(
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
    "DIAMOND PLATINUM" to All(
        name="DIAMOND PLATINUM",
        price = "Ksh 3500",
        features = listOf(
            "Full Digital Strategy Management",
            "Dedicated Account Manager",
            "24/7 Support & Custom Reports"
        )
    ),
    "DIAMOND PLATINUM GOLD" to All(
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
