package ru.geekbrains.noteapp.model.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ru.geekbrains.noteapp.model.data.Note
import ru.geekbrains.noteapp.model.data.User
import ru.geekbrains.noteapp.model.exception.NoAuthException


private const val ALL_NOTES = "all_notes"
private const val ALL_USERS = "all_users"

class RemoteDataProviderImpl : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotes().addSnapshotListener { qSnapshot, error ->
                value = error?.let { NoteResult.Error(error) }
                    ?: qSnapshot?.let { snapshot ->
                        val notes = snapshot.documents.map { doc ->
                            doc.toObject(Note::class.java)
                        }
                        NoteResult.Success(notes)
                    }
            }
        } catch (error: Throwable) {
            value = NoteResult.Error(error)
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotes().document(id).get()
                    .addOnSuccessListener { snapshot ->
                        Log.d(TAG, "get note OnSuccess")
                        value = NoteResult.Success(snapshot.toObject(Note::class.java))
                    }.addOnFailureListener { ex ->
                        Log.d(TAG, "get note OnFailure")
                        value = NoteResult.Error(ex)
                    }
            } catch (error: Throwable) {
                value = NoteResult.Error(error)
            }
        }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotes().document(note.id).set(note)
                .addOnSuccessListener {
                    OnSuccessListener<Void> {
                        Log.d(TAG, "save note OnSuccess: $note")
                        value = NoteResult.Success(note)
                    }
                }.addOnFailureListener {
                    OnFailureListener { ex ->
                        Log.d(TAG, "save note OnFailure: $note")
                        value = NoteResult.Error(ex)
                    }
                }
        } catch (error: Throwable) {
            value = NoteResult.Error(error)
        }
    }

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(
                name = it.displayName ?: "",
                email = it.email ?: ""
            )
        }
    }

    private fun getUserNotes() = currentUser?.let {
        db.collection(ALL_USERS).document(it.uid).collection(ALL_NOTES)
    } ?: throw NoAuthException()

    companion object {
        private val TAG = RemoteDataProviderImpl::class.java.simpleName
    }
}