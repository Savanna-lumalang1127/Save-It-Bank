package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EmployeesModel")
    fun getAllEmployees(): Flow<List<EmployeeModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emp: EmployeeModel)

    @Update
    suspend fun update(emp: EmployeeModel)

    @Delete
    suspend fun delete(emp: EmployeeModel)
}