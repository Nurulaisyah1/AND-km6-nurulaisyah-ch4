package com.foodapps.data.model

import com.google.firebase.auth.FirebaseUser


data class User(
    val id: String,
    val name: String,
    val email: String,
)

fun FirebaseUser?.toUser() =
    this?.let {
        User(
            id = this.uid,
            name = this.displayName.orEmpty(),
            email = this.email.orEmpty(),

        )
    }