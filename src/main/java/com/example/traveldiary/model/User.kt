package com.example.traveldiary.model

data class Name(val title: String, val first: String, val last: String)

data class User(val name: Name)

data class UserResponse(val results: List<User>)
