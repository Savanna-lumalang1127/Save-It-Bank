package com.example.myapplication

import kotlinx.coroutines.flow.Flow

class EmployeeRepository(
    private val empdao: EmployeeDao
) {

    val employees: Flow<List<EmployeeModel>> = empdao.getAllEmployees()

    suspend fun addEmployee(emp: EmployeeModel) {
        empdao.insert(emp)
    }

    suspend fun updateEmployee(emp: EmployeeModel) {
        empdao.update(emp)
    }

    suspend fun deleteEmployee(emp: EmployeeModel) {
        empdao.delete(emp)
    }
}