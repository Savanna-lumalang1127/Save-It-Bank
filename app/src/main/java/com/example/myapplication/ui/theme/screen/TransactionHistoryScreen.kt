package com.example.myapplication.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.TransactionModel
import com.example.myapplication.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val ScreenBackground = Color(0xFFF5F0E8)
private val CardNavy = Color(0xFF111B2E)
private val Positive = Color(0xFF4CD964)
private val Negative = Color(0xFFFF5A5F)

private const val TYPE_INCOME = "income"
private const val TYPE_EXPENSE = "expense"

/** Turns 1500 + expense into "-₱1,500.00" and 5000 + income into "+₱5,000.00" */
private fun formatAmount(value: Double, type: String): String {
    val sign = if (type == TYPE_EXPENSE) "-" else "+"
    return sign + "₱" + String.format(Locale.US, "%,.2f", value)
}

private fun today(): String =
    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

@Composable
fun TransactionHistoryScreen(
    viewModel: UserViewModel,
    navController: NavController
) {
    val transactions by viewModel.allTransactions.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text("← Back", color = CardNavy)
            }

            Text(
                text = "Transaction History",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CardNavy
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (transactions.isEmpty()) {
                Text(
                    text = "No transactions yet. Tap + to add one.",
                    color = CardNavy
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                    items(transactions, key = { it.id }) { transaction ->
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

        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = CardNavy,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add transaction")
        }
    }

    if (showAddDialog) {
        TransactionDialog(
            heading = "Add transaction",
            initialTitle = "",
            initialAmount = "",
            initialType = TYPE_INCOME,
            onDismiss = { showAddDialog = false },
            onSave = { title, amount, type ->
                viewModel.addTransaction(
                    title = title,
                    amount = amount,
                    date = today(),
                    type = type
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
fun TransactionRow(
    transaction: TransactionModel,
    onDelete: (String) -> Unit,
    onEdit: (id: String, title: String, amount: String, type: String) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    val isNegative = transaction.amount.trim().startsWith("-")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardNavy)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = transaction.date,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 11.sp
                )
            }

            Text(
                text = transaction.amount,
                color = if (isNegative) Negative else Positive,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            IconButton(onClick = { showEditDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit transaction",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }

            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete transaction",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }

    if (showEditDialog) {
        TransactionDialog(
            heading = "Edit transaction",
            initialTitle = transaction.title,
            initialAmount = transaction.amount.filter { it.isDigit() || it == '.' },
            initialType = if (isNegative) TYPE_EXPENSE else TYPE_INCOME,
            onDismiss = { showEditDialog = false },
            onSave = { title, amount, type ->
                onEdit(transaction.id, title, amount, type)
                showEditDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete transaction?") },
            text = { Text("\"${transaction.title}\" will be permanently removed.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(transaction.id)
                    showDeleteDialog = false
                }) { Text("Delete", color = Negative) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

/** One dialog used for both adding and editing. */
@Composable
fun TransactionDialog(
    heading: String,
    initialTitle: String,
    initialAmount: String,
    initialType: String,
    onDismiss: () -> Unit,
    onSave: (title: String, amount: String, type: String) -> Unit
) {
    var title by remember { mutableStateOf(initialTitle) }
    var amountText by remember { mutableStateOf(initialAmount) }
    var type by remember { mutableStateOf(initialType) }

    val amountValue = amountText.toDoubleOrNull()
    val canSave = title.isNotBlank() && amountValue != null && amountValue > 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(heading) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title (e.g. Wallet Cash-In)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { input ->
                        val cleaned = input.filter { it.isDigit() || it == '.' }
                        if (cleaned.count { it == '.' } <= 1) amountText = cleaned
                    },
                    label = { Text("Amount (₱)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (type == TYPE_INCOME) {
                        Button(
                            onClick = { type = TYPE_INCOME },
                            colors = ButtonDefaults.buttonColors(containerColor = CardNavy),
                            modifier = Modifier.weight(1f)
                        ) { Text("Money in") }
                        OutlinedButton(
                            onClick = { type = TYPE_EXPENSE },
                            modifier = Modifier.weight(1f)
                        ) { Text("Money out") }
                    } else {
                        OutlinedButton(
                            onClick = { type = TYPE_INCOME },
                            modifier = Modifier.weight(1f)
                        ) { Text("Money in") }
                        Button(
                            onClick = { type = TYPE_EXPENSE },
                            colors = ButtonDefaults.buttonColors(containerColor = CardNavy),
                            modifier = Modifier.weight(1f)
                        ) { Text("Money out") }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(title.trim(), formatAmount(amountValue ?: 0.0, type), type)
                },
                enabled = canSave
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}