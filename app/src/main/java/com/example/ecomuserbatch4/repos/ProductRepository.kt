package com.example.ecomuserbatch4.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecomuserbatch4.models.OrderSettings
import com.example.ecomuserbatch4.models.Product
import com.example.ecomuserbatch4.utils.collectionCategory
import com.example.ecomuserbatch4.utils.collectionOrderSettings
import com.example.ecomuserbatch4.utils.collectionProduct
import com.example.ecomuserbatch4.utils.documentOrderSettings
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {

    val db = FirebaseFirestore.getInstance()


    fun getOrderSettings() : LiveData<OrderSettings> {
        val settingsLD = MutableLiveData<OrderSettings>()
        db.collection(collectionOrderSettings).document(documentOrderSettings)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                settingsLD.value = value?.toObject(OrderSettings::class.java)
            }
        return settingsLD
    }

    fun getAllCategories() : LiveData<List<String>> {
        val catLD = MutableLiveData<List<String>>()
        db.collection(collectionCategory)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<String>()
                for (doc in value!!.documents) {
                    tempList.add(doc.get("name").toString())
                }
                catLD.value = tempList
            }
        return catLD
    }

    fun getAllProducts() : LiveData<List<Product>> {
        val productLD = MutableLiveData<List<Product>>()
        db.collection(collectionProduct)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Product>()
                for (doc in value!!.documents) {
                    doc.toObject(Product::class.java)?.let { tempList.add(it) }
                }
                productLD.value = tempList
            }
        return productLD
    }

    fun getProductByProductId(id: String) : LiveData<Product> {
        val productLD = MutableLiveData<Product>()
        db.collection(collectionProduct).document(id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                productLD.value = value?.toObject(Product::class.java)
            }
        return productLD
    }
}