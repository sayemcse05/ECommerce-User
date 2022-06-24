package com.example.ecomuserbatch4.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    enum class Authentication {
        AUTHENTICATED, UNAUTHENTICATED
    }
    val authLD: MutableLiveData<Authentication> = MutableLiveData()
    val errMsgLD: MutableLiveData<String> = MutableLiveData()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        if (auth.currentUser != null) {
            authLD.value = Authentication.AUTHENTICATED
        } else {
            authLD.value = Authentication.UNAUTHENTICATED
        }
    }

    fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                authLD.value = Authentication.AUTHENTICATED
            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage
            }
    }
    fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                authLD.value = Authentication.AUTHENTICATED
            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage
            }
    }
}