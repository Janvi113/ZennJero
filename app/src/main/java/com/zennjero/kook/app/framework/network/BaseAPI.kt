package com.zennjero.kook.app.framework.network

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

/**
 * This class contains a reference of Firestore data base as db
 * and some functions for basic CRUD operations
 *
 * Every API class must extend this BaseAPI class
 */

open class BaseAPI {

    private val TAG = BaseAPI::class.java.simpleName
    protected val db = FirebaseFirestore.getInstance()

    /**
     * This method will save a document with a random document id
     * in a Collection
     */
    protected fun saveDocument(
        collectionName: String,
        data: Any,
        onSuccess: (DocumentReference?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(collectionName)
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Document saved to $collectionName")
                onSuccess(documentReference)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed to save document to $collectionName")
                onFailure(exception)
            }
    }

    /**
     * This method will save a document with provided document id
     * in a Collection
     */
    protected fun saveDocument(
        collectionName: String,
        documentId: String,
        data: Any,
        onSuccess: (Void?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(collectionName)
            .document(documentId)
            .set(data)
            .addOnSuccessListener { void ->
                Log.d(TAG, "Document saved to $collectionName with id $documentId")
                onSuccess(void)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed to save document to $collectionName with id $documentId")
                onFailure(exception)
            }
    }

    /**
     * This method will save a list of document with random document id's
     * in a Collection
     */
    protected fun saveDocumentList(
        collectionName: String,
        data: List<Any>,
        onSuccess: (Void?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val collectionRef = db.collection(collectionName)
        db.runBatch { batch ->
            // A batch of writes completes atomically
            data.forEach { any ->
                batch.set(collectionRef.document(UUID.randomUUID()!!.toString()), any)
            }
        }.addOnSuccessListener { void ->
            Log.d(TAG, "Document list saved to $collectionName")
            onSuccess(void)
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Failed to save document list to $collectionName")
            onFailure(exception)
        }
    }

    /**
     * This method will read a document with provided document id
     * in a collection
     */
    protected fun readDocument(
        collectionName: String,
        documentId: String,
        onSuccess: (DocumentSnapshot?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(collectionName)
            .document(documentId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d(TAG, "Document $documentId fetched from $collectionName")
                onSuccess(documentSnapshot)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed to fetch document $documentId from $collectionName")
                onFailure(exception)
            }
    }

    /**
     * This method will update a document with provided document id
     * in a collection
     */

    protected fun updateDocument(
        collectionName: String,
        documentId: String,
        data: Any,
        onSuccess: (Void?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        saveDocument(collectionName, documentId, data, onSuccess, onFailure)
    }

    /**
     * This method will delete a document with provided document id
     * in a collection
     */

    protected fun deleteDocument(
        collectionName: String,
        documentId: String,
        onSuccess: (Void?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(collectionName)
            .document(documentId)
            .delete()
            .addOnSuccessListener { void ->
                Log.d(TAG, "Document $documentId deleted from $collectionName")
                onSuccess(void)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed to delete document $documentId from $collectionName")
                onFailure(exception)
            }
    }
}