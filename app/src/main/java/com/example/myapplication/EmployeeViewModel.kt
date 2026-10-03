package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val repository: EmployeeRepository
) : ViewModel() {

    val emplist = repository.employees

    fun deleteEmployee(userid: Int) {
        viewModelScope.launch {
            val employee = emplist.first().find {
                it.id == userid
            }

            employee?.let {
                repository.deleteEmployee(it)
            }
        }
    }

    fun updateEmployee(
        empid: Int,
        empname: String,
        emppost: String
    ) {
        viewModelScope.launch {
            val employeeList = emplist.first()
            val updatedEmployee = employeeList.find { it.id == empid }?.copy(
                name = empname,
                position = emppost
                // If your data class has an image field, match its exact name:
                // imageRes = R.drawable.profile
            )

            updatedEmployee?.let {
                repository.updateEmployee(it)
            }
        }
    }
}