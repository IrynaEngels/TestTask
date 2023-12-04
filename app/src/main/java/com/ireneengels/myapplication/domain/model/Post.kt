package com.ireneengels.myapplication.domain.model

data class Post(
    val id: Long,
    val user_name: String,
    val user_id: String,
    val user_pic: String,
    val message: String,
    val photo: String?,
    val date: String
)
