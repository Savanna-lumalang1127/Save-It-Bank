package com.example.myapplication

data class TransactionModel(
    val id: String = "",
    val title: String = "",
    val amount: String = "",
    val date: String = "",
    val type: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
