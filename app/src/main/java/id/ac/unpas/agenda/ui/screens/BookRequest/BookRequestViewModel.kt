package id.ac.unpas.agenda.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.unpas.agenda.base.LiveCoroutinesViewModel
import id.ac.unpas.agenda.models.BookRequest
import id.ac.unpas.agenda.repositories.BookRequestRepository
import javax.inject.Inject

@HiltViewModel
class BookRequestViewModel @Inject constructor(private val todoRepository: BookRequestRepository) : LiveCoroutinesViewModel() {

    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone: LiveData<Boolean> = _isDone

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _item: MutableLiveData<BookRequest> = MutableLiveData()
    val item: LiveData<BookRequest> = _item

    private val _isDeleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _book: MutableLiveData<Boolean> = MutableLiveData(false)
    val requests: LiveData<List<BookRequest>> = _book.switchMap {
        _isLoading.postValue(true)
        launchOnViewModelScope {
            todoRepository.loadItems(
                onSuccess = {
                    _isLoading.postValue(false)
                },
                onError = {
                    _isLoading.postValue(false)
                    Log.e("TodoViewModel", it)
                }
            ).asLiveData()
        }
    }

    suspend fun insert(
        id: String,
        library_book_id: String,
        library_member_id: String,
        start_date: String,
        end_date: String,
        status: String,
        created_at: String,
        updated_at: String
    ) {
        _isLoading.postValue(true)
        val bookRequest = BookRequest(
            id,
            library_book_id,
            library_member_id,
            start_date,
            end_date,
            status,
            created_at,
            updated_at
        )
        todoRepository.insert(
            bookRequest,
            onSuccess = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
            },
            onError = {
                _isLoading.postValue(false)
                Log.e("TodoViewModel", it)
            }
        )
    }

    suspend fun update(
        id: String,
        library_book_id: String,
        library_member_id: String,
        start_date: String,
        end_date: String,
        status: String,
        created_at: String,
        updated_at: String
    ) {
        _isLoading.postValue(true)
        val bookRequest = BookRequest(
            id,
            library_book_id,
            library_member_id,
            start_date,
            end_date,
            status,
            created_at,
            updated_at
        )
        todoRepository.update(
            bookRequest,
            onSuccess = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
            },
            onError = {
                _isLoading.postValue(false)
                Log.e("TodoViewModel", it)
            }
        )
    }

    suspend fun delete(id: String) {
        _isLoading.postValue(true)
        todoRepository.delete(id,
            onSuccess = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
                _isDeleted.postValue(true)
            },
            onError = {
                _isLoading.postValue(false)
                Log.e("TodoViewModel", it)
            }
        )
    }

    suspend fun find(id: String) {
        val book = todoRepository.find(id)
        book?.let {
            _item.postValue(it)
        }
    }
}
