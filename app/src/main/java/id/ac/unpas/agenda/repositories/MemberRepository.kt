package id.ac.unpas.agenda.repositories

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.networks.MemberApi
import id.ac.unpas.agenda.persistences.MemberDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepository @Inject constructor(private val api: MemberApi, private val dao: MemberDao) {

    @WorkerThread
    fun loadItems(onSuccess: () -> Unit,
                  onError: (String) -> Unit) = flow {
        val list: List<Member> = dao.findAll()
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
    suspend fun insert(todo: Member,
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

    suspend fun update(todo: Member,
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

    suspend fun findByName(name: String): Member? {
        return dao.findByName(name)
    }

    suspend fun findId(id: String): Member? {
        return dao.findId(id)
    }
}