package id.ac.unpas.agenda.persistences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.unpas.agenda.models.BookRequest

@Dao
interface BookRequestDao {

    @Query("select * from book_request")
    fun loadAll(): LiveData<List<BookRequest>>

    @Query("select * from book_request")
    suspend fun findAll(): List<BookRequest>

    @Query("select * from book_request where id = :id")
    fun load(id: String): LiveData<BookRequest>

    @Query("select * from book_request where id = :id")
    suspend fun getById(id: String): BookRequest?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg items: BookRequest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<BookRequest>)

    @Query("delete from book_request where id = :id")
    suspend fun delete(id: String)

    @Query("select * from book_request where id = :id")
    suspend fun find(id: kotlin.String): BookRequest?

    @Delete
    suspend fun delete(item: BookRequest)
}