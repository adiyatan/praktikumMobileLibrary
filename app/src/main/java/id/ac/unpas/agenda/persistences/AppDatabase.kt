package id.ac.unpas.agenda.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.models.BookRequest

@Database(entities = [Book::class, Member::class, BookRequest::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun memberDao(): MemberDao
    abstract fun bookRequestDao(): BookRequestDao

    companion object {
        const val DATABASE_NAME = "baruuuuuu"
    }
}
