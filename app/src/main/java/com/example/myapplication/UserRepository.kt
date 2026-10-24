package com.example.myapplication

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("transactions")
    private val accountDoc = db.collection("settings").document("account")

    // READ (real-time)
    fun getAllTransactions(): Flow<List<TransactionModel>> = callbackFlow {
        val listener = collection
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    Log.d("SaveIt", "Error: $exception")
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snapshots?.documents?.map { doc ->
                    TransactionModel(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        amount = doc.getString("amount") ?: "",
                        date = doc.getString("date") ?: "",
                        type = doc.getString("type") ?: "",
                        createdAt = doc.getLong("createdAt") ?: 0L
                    )
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    // CREATE
    suspend fun insert(transaction: TransactionModel) {
        val data = hashMapOf(
            "title" to transaction.title,
            "amount" to transaction.amount,
            "date" to transaction.date,
            "type" to transaction.type,
            "createdAt" to transaction.createdAt
        )
        Log.d("SaveIt", "Insert started")
        try {
            val ref = withTimeout(10_000) { collection.add(data).await() }
            Log.d("SaveIt", "Saved with id: ${ref.id}")
        } catch (e: Exception) {
            Log.d("SaveIt", "Insert failed: $e")
            throw e
        }
    }

    // UPDATE
    suspend fun update(transaction: TransactionModel) {
        val data = mapOf(
            "title" to transaction.title,
            "amount" to transaction.amount,
            "date" to transaction.date,
            "type" to transaction.type
        )
        try {
            withTimeout(10_000) { collection.document(transaction.id).update(data).await() }
            Log.d("SaveIt", "Updated: ${transaction.id}")
        } catch (e: Exception) {
            Log.d("SaveIt", "Update failed: $e")
            throw e
        }
    }

    // DELETE
    suspend fun delete(id: String) {
        try {
            withTimeout(10_000) { collection.document(id).delete().await() }
            Log.d("SaveIt", "Deleted: $id")
        } catch (e: Exception) {
            Log.d("SaveIt", "Delete failed: $e")
            throw e
        }
    }

    // STARTING BALANCE (settings/account)
    fun getStartingBalance(): Flow<Double> = callbackFlow {
        val listener = accountDoc.addSnapshotListener { snap, exception ->
            if (exception != null) {
                Log.d("SaveIt", "Balance error: $exception")
                trySend(0.0)
                return@addSnapshotListener
            }
            trySend(snap?.getDouble("startingBalance") ?: 0.0)
        }
        awaitClose { listener.remove() }
    }

    suspend fun setStartingBalance(value: Double) {
        try {
            withTimeout(10_000) {
                accountDoc.set(mapOf("startingBalance" to value)).await()
            }
            Log.d("SaveIt", "Starting balance saved: $value")
        } catch (e: Exception) {
            Log.d("SaveIt", "Balance save failed: $e")
            throw e
        }
    }
}