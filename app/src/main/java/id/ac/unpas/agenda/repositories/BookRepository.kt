package id.ac.unpas.agenda.repositories

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.networks.BookApi
import id.ac.unpas.agenda.persistences.BookDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BookApi, private val dao: BookDao) {

    @WorkerThread
    fun loadItems(onSuccess: () -> Unit,
                  onError: (String) -> Unit) = flow {
        val list: List<Book> = dao.findAll()
        api.findAll()
            .suspendOnSuccess {
                data.whatIfNotNull {
                    dao.upsert(it.data)
                    val localList = dao.findAll()
                    emit(localList)
                    onSuccess()
                }
            }
            .suspendOnError {
                emit(list)
                onError(message())
            }
            .suspendOnException {
                emit(list)
                onError(message())
            }
    }
    suspend fun insert(todo: Book,
                       onSuccess: () -> Unit,
                       onError: (String) -> Unit) {
        dao.upsert(todo)
        api.insert(todo)
            .suspendOnSuccess {
                data.whatIfNotNull {
                    if (it.success) {
                        onSuccess()
                    } else {
                        onError(it.message)
                    }
                }
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun update(todo: Book,
                       onSuccess: () -> Unit,
                       onError: (String) -> Unit) {
        dao.upsert(todo)
        api.update(todo.id, todo)
            .suspendOnSuccess {
                data.whatIfNotNull {
                    if (it.success) {
                        onSuccess()
                    } else {
                        onError(it.message)
                    }
                }
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun delete(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        dao.delete(id)
        api.delete(id)
            .suspendOnSuccess {
                data.whatIfNotNull {
                    if (it.success) {
                        onSuccess()
                    } else {
                        onError(it.message)
                    }
                }
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun find(id: String) = dao.find(id)

    // buat cek title sudah ada atau belum
    suspend fun existsByTitle(title: String) = dao.existsByTitle(title) > 0
}