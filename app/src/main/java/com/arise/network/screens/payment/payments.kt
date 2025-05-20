/*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arise.network.models.PricingPackage
import com.arise.network.models.pricingPackages // Import the pricingPackages map
import com.arise.network.screens.payment.PaymentMethod
import java.net.URLDecoder

@Composable
fun PaymentScreen(navController: NavHostController, packageName: String) {
    val decodedName = java.net.URLDecoder.decode(packageName, "UTF-8")
    val selectedPackage = pricingPackages[decodedName]

    // Check if selectedPackage is null and show an error message instead of crashing
    if (selectedPackage == null) {
        Text(
            text = "Invalid package: $decodedName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        return // Exit early if package is invalid
    }

    var selectedMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    val inputData = remember { mutableStateMapOf<String, String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Pay for ${selectedPackage.name}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Price: ${selectedPackage.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("What you'll get:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        selectedPackage.features.forEach { feature ->
            Text("• $feature", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Choose Payment Method:", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(8.dp))

        PaymentMethod.values().forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = method == selectedMethod,
                    onClick = { selectedMethod = method }
                )
                Text(text = method.displayName, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedMethod) {
            PaymentMethod.Mpesa -> {
                OutlinedTextField(
                    value = inputData["phone"] ?: "",
                    onValueChange = { inputData["phone"] = it },
                    label = { Text("Mpesa Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            PaymentMethod.PayPal -> {
                OutlinedTextField(
                    value = inputData["email"] ?: "",
                    onValueChange = { inputData["email"] = it },
                    label = { Text("PayPal Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            PaymentMethod.DebitCard, PaymentMethod.ATMCard -> {
                OutlinedTextField(
                    value = inputData["cardNumber"] ?: "",
                    onValueChange = { inputData["cardNumber"] = it },
                    label = { Text("Card Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = inputData["expiry"] ?: "",
                    onValueChange = { inputData["expiry"] = it },
                    label = { Text("Expiry Date (MM/YY)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = inputData["cvv"] ?: "",
                    onValueChange = { inputData["cvv"] = it },
                    label = { Text("CVV") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            null -> {}
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                println("Processing $selectedMethod for ${selectedPackage.name} at ${selectedPackage.price}")
                // TODO: Real payment logic
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedMethod != null
        ) {
            Text("Make Payment")
        }
    }
}
*/
