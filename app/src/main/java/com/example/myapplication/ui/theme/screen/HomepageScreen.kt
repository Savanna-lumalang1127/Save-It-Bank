package com.example.myapplication.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.UserViewModel
import com.example.myapplication.navigation.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Local colors (swap these for your MidnightBlue / Champagne / UmberDark if you like)
private val HomeBackground = Color(0xFFF5F0E8)
private val HomeNavy = Color(0xFF111B2E)
private val HomeChampagne = Color(0xFFF7E7CE)
private val HomeUmber = Color(0xFF3B2A1E)

private const val TYPE_IN = "income"
private const val TYPE_OUT = "expense"

private fun formatMoney(value: Double, type: String): String {
    val sign = if (type == TYPE_OUT) "-" else "+"
    return sign + "₱" + String.format(Locale.US, "%,.2f", value)
}

private fun todayText(): String =
    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel) {
    val transactions by viewModel.allTransactions.collectAsState()
    val balance by viewModel.totalBalance.collectAsState()
    val startingBalance by viewModel.startingBalance.collectAsState()

    var showBalanceDialog by remember { mutableStateOf(false) }
    var addType by remember { mutableStateOf<String?>(null) } // null = dialog closed

    Scaffold(
        containerColor = HomeBackground,
        bottomBar = {
            NavigationBar(containerColor = HomeNavy) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Wallet, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = HomeChampagne,
                        selectedTextColor = HomeChampagne,
                        indicatorColor = HomeUmber,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History") },
                    selected = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = HomeChampagne,
                        selectedTextColor = HomeChampagne,
                        indicatorColor = HomeUmber,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    onClick = { navController.navigate(Screen.Transactions.route) }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            // Greeting
            item {
                Column {
                    Text(
                        text = "Welcome back,",
                        fontSize = 12.sp,
                        color = HomeNavy.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Savanna G.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = HomeNavy
                    )
                }
            }

            // Balance card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = HomeNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Total Savings Balance",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₱ " + String.format(Locale.US, "%,.2f", balance),
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { showBalanceDialog = true }
                        )
                        Text(
                            text = "Tap the balance to set your starting amount",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = { addType = TYPE_OUT },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HomeChampagne,
                                    contentColor = HomeNavy
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                Text("Transfer")
                            }
                            Button(
                                onClick = { addType = TYPE_IN },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HomeChampagne,
                                    contentColor = HomeNavy
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                Text("Top Up")
                            }
                        }
                    }
                }
            }

            // Recent transactions header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = HomeNavy
                    )
                    Text(
                        text = "See all",
                        fontSize = 13.sp,
                        color = HomeNavy,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Transactions.route)
                        }
                    )
                }
            }

            if (transactions.isEmpty()) {
                item {
                    Text(
                        text = "No transactions yet. Use Top Up or Transfer to add one.",
                        color = HomeNavy
                    )
                }
            } else {
                items(transactions.take(5), key = { it.id }) { transaction ->
                    TransactionRow(
                        transaction = transaction,
                        onDelete = { id -> viewModel.deleteTransaction(id) },
                        onEdit = { id, title, amount, type ->
                            viewModel.updateTransaction(
                                id = id,
                                title = title,
                                amount = amount,
                                date = transaction.date,
                                type = type
                            )
                        }
                    )
                }
            }
        }
    }

    // Edit the starting balance
    if (showBalanceDialog) {
        EditBalanceDialog(
            current = startingBalance,
            onDismiss = { showBalanceDialog = false },
            onSave = { value ->
                viewModel.updateStartingBalance(value)
                showBalanceDialog = false
            }
        )
    }

    // Add a transaction (Transfer = money out, Top Up = money in)
    addType?.let { type ->
        TransactionDialog(
            heading = if (type == TYPE_OUT) "Transfer" else "Top Up",
            initialTitle = "",
            initialAmount = "",
            initialType = type,
            onDismiss = { addType = null },
            onSave = { title, amount, savedType ->
                viewModel.addTransaction(
                    title = title,
                    amount = amount,
                    date = todayText(),
                    type = savedType
                )
                addType = null
            }
        )
    }
}

@Composable
fun EditBalanceDialog(
    current: Double,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var text by remember { mutableStateOf(if (current == 0.0) "" else current.toString()) }
    val value = text.toDoubleOrNull()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set starting balance") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { input ->
                    val cleaned = input.filter { it.isDigit() || it == '.' }
                    if (cleaned.count { it == '.' } <= 1) text = cleaned
                },
                label = { Text("Starting balance (₱)") },
                supportingText = { Text("Your total = this + all transactions") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(value ?: 0.0) },
                enabled = value != null
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}