package id.ac.unpas.agenda.persistences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.unpas.agenda.models.Book

@Dao
interface BookDao {

    @Query("select * from book")
    fun loadAll(): LiveData<List<Book>>

    @Query("select * from book")
    suspend fun findAll(): List<Book>

    @Query("select * from book where id = :id")
    fun load(id: String): LiveData<Book>

    @Query("select * from book where id = :id")
    suspend fun getById(id: String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg items: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<Book>)

    @Query("delete from book where id = :id")
    suspend fun delete(id: String)

    @Query("select * from book where id = :id")
    suspend fun find(id: kotlin.String): Book?

    @Delete
    suspend fun delete(item: Book)

    @Query("SELECT COUNT(*) FROM book WHERE title = :title")
    suspend fun existsByTitle(title: String): Int

    @Query("SELECT * FROM book WHERE title = :title LIMIT 1")
    suspend fun findByTitle(title: String): Book?
}