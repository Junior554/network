package com.arise.network.screens.payment


enum class PaymentMethod(val displayName: String) {
    Mpesa("Mpesa"),
    PayPal("PayPal"),
    DebitCard("Debit Card"),
    ATMCard("ATM Card")
}
