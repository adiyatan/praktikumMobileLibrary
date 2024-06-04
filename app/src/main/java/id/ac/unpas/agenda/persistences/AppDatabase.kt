package id.ac.unpas.agenda.persistences

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
        private var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "baruuuuuu"

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            }
            return INSTANCE!!
        }
    }
}