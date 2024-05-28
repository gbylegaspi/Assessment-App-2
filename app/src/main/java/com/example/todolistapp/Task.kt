package com.example.todolistapp

data class Task(
    val taskName: String,
    var isCompleted: Boolean = false
)