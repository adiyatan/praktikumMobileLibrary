package id.ac.unpas.agenda.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.unpas.agenda.base.LiveCoroutinesViewModel
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.repositories.BookRepository
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val todoRepository: BookRepository) : LiveCoroutinesViewModel() {

    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone: LiveData<Boolean> = _isDone

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _item: MutableLiveData<Book> = MutableLiveData()
    val item: LiveData<Book> = _item

    private val _isDeleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _book: MutableLiveData<Boolean> = MutableLiveData(false)
    val books : LiveData<List<Book>> = _book.switchMap {
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

    //function existsByTitle
    suspend fun existsByTitle(title: String): Boolean {
        return todoRepository.existsByTitle(title)
    }

    //find by title
    suspend fun findByTitle(title: String): Book? {
        return todoRepository.findByTitle(title)
    }

    suspend fun insert(id: String,
                       title: String,
                       author: String,
                       released_date: String,
                       stock: Int,
                       created_at: String,
                       update_at: String) {
        _isLoading.postValue(true)
        todoRepository.insert(Book(id, title, author, released_date, stock, created_at, update_at),
            onSuccess = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
            },
            onError = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
            }
        )
    }

    suspend fun update(id: String,
                       title: String,
                       author: String,
                       released_date: String,
                       stock: Int,
                       created_at: String,
                       update_at: String) {
        _isLoading.postValue(true)
        todoRepository.update(Book(id, title, author, released_date, stock, created_at, update_at),
            onSuccess = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
            },
            onError = {
                _isLoading.postValue(false)
                _isDone.postValue(true)
                _book.postValue(true)
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
            }
        ) {
            _isLoading.postValue(false)
            _isDone.postValue(true)
            _book.postValue(true)
            _isDeleted.postValue(false)
        }
    }

    suspend fun find(id: String) {
        val book = todoRepository.find(id)
        book?.let {
            _item.postValue(it)
        }
    }
}