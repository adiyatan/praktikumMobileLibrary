package id.ac.unpas.agenda.persistences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.unpas.agenda.models.Member

@Dao
interface MemberDao {

    @Query("select * from member")
    fun loadAll(): LiveData<List<Member>>

    @Query("select * from member")
    suspend fun findAll(): List<Member>

    @Query("select * from member where id = :id")
    fun load(id: String): LiveData<Member>

    @Query("select * from member where id = :id")
    suspend fun getById(id: String): Member?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg items: Member)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<Member>)

    @Query("delete from member where id = :id")
    suspend fun delete(id: String)

    @Query("select * from member where id = :id")
    suspend fun find(id: kotlin.String): Member?

    @Delete
    suspend fun delete(item: Member)

    @Query("SELECT * FROM member WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Member?
}