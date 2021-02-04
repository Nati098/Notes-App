package ru.geekbrains.noteapp.model.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import ru.geekbrains.noteapp.model.data.Note


private const val ALL_NOTES = "all_notes"

class RemoteDataProviderImpl : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesRef = db.collection(ALL_NOTES)

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        notesRef.addSnapshotListener { qSnapshot, error ->
            value = error?.let {NoteResult.Error(error)}
                ?: qSnapshot?.let { snapshot ->
                    val notes = snapshot.documents.map { doc ->
                        doc.toObject(Note::class.java)
                    }
                    NoteResult.Success(notes)
                }
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        notesRef.document(id).get()
            .addOnSuccessListener { snapshot ->
                Log.d(TAG, "get note OnSuccess")
                value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener { ex ->
                Log.d(TAG, "get note OnFailure")
                value = NoteResult.Error(ex)
            }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        notesRef.document(note.id).set(note)
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
    }

    companion object {
        private val TAG = RemoteDataProviderImpl::class.java.simpleName
    }
}