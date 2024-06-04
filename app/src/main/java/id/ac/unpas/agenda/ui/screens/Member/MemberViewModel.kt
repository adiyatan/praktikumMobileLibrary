package id.ac.unpas.agenda.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.unpas.agenda.base.LiveCoroutinesViewModel
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.repositories.MemberRepository
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(private val todoRepository: MemberRepository) : LiveCoroutinesViewModel() {

    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone: LiveData<Boolean> = _isDone

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _item: MutableLiveData<Member> = MutableLiveData()
    val item: LiveData<Member> = _item

    private val _isDeleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _book: MutableLiveData<Boolean> = MutableLiveData(false)
    val members : LiveData<List<Member>> = _book.switchMap {
        _isLoading.postValue(true)
        launchOnViewModelScope {
            todoRepository.loadItems(
                onSuccess = {
                    _isLoading.postValue(false)
                },
                onError = {
                    _isLoading.postValue(false)
                    Log.e("MemberViewModel", it)
                }
            ).asLiveData()
        }
    }

    suspend fun insert(id: String,
                       name: String,
                       address: String,
                       phone: String,
                       created_at: String,
                       update_at: String) {
        _isLoading.postValue(true)
        todoRepository.insert(Member(id, name, address, phone, created_at, update_at),
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
                       name: String,
                       address: String,
                       phone: String,
                       created_at: String,
                       update_at: String) {
        _isLoading.postValue(true)
        todoRepository.update(Member(id, name, address, phone, created_at, update_at),
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

    suspend fun findByName(name: String): Member? {
        return todoRepository.findByName(name)
    }

    suspend fun findId(id: String): Member? {
        return todoRepository.findId(id)
    }
}