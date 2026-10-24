package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    val allTransactions: StateFlow<List<TransactionModel>> =
        repository.getAllTransactions().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val startingBalance: StateFlow<Double> =
        repository.getStartingBalance().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    // Total = starting balance + every transaction (money out counts as negative)
    val totalBalance: StateFlow<Double> =
        combine(startingBalance, allTransactions) { start, list ->
            start + list.sumOf { parseAmount(it.amount) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addTransaction(title: String, amount: String, date: String, type: String) {
        viewModelScope.launch {
            try {
                repository.insert(
                    TransactionModel(title = title, amount = amount, date = date, type = type)
                )
            } catch (e: Exception) {
                Log.d("SaveIt", "Add failed: $e")
            }
        }
    }

    fun updateTransaction(id: String, title: String, amount: String, date: String, type: String) {
        viewModelScope.launch {
            try {
                repository.update(
                    TransactionModel(id = id, title = title, amount = amount, date = date, type = type)
                )
            } catch (e: Exception) {
                Log.d("SaveIt", "Update failed: $e")
            }
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            try {
                repository.delete(id)
            } catch (e: Exception) {
                Log.d("SaveIt", "Delete failed: $e")
            }
        }
    }

    fun updateStartingBalance(value: Double) {
        viewModelScope.launch {
            try {
                repository.setStartingBalance(value)
            } catch (e: Exception) {
                Log.d("SaveIt", "Balance update failed: $e")
            }
        }
    }
}

/** "+₱5,000.00" -> 5000.0   and   "-₱1,500.00" -> -1500.0 */
fun parseAmount(text: String): Double {
    val number = text.filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 0.0
    return if (text.trim().startsWith("-")) -number else number
}