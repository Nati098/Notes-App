package ru.geekbrains.noteapp.model.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import ru.geekbrains.noteapp.model.data.Note


private const val ALL_NOTES = "all_notes"

class RemoteDataProviderImpl : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesRef = db.collection(ALL_NOTES)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val res = MutableLiveData<NoteResult>()
        notesRef.addSnapshotListener { qSnapshot, error ->
            if (error != null) {
                res.value = NoteResult.Error(error)
            }
            else if (qSnapshot != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in qSnapshot) {
                    notes.add(doc.toObject(Note::class.java))
                }

                res.value = NoteResult.Success(notes)
            }
        }

        return res
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val res = MutableLiveData<NoteResult>()
        notesRef.document(id).get()
            .addOnSuccessListener { snapshot ->
                Log.d(TAG, "get note OnSuccess")
                res.value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener { ex ->
                Log.d(TAG, "get note OnFailure")
                res.value = NoteResult.Error(ex)
            }

        return res
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val res = MutableLiveData<NoteResult>()
        notesRef.document(note.id).set(note)
            .addOnSuccessListener {
                OnSuccessListener<Void> {
                    Log.d(TAG, "save note OnSuccess: $note")
                    res.value = NoteResult.Success(note)
                }
            }.addOnFailureListener {
                OnFailureListener { ex ->
                    Log.d(TAG, "save note OnFailure: $note")
                    res.value = NoteResult.Error(ex)
                }
            }

        return res
    }

    companion object {
        private val TAG = RemoteDataProviderImpl::class.java.simpleName
    }
}