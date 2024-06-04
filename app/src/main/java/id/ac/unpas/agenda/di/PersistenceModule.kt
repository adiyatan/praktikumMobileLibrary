package id.ac.unpas.agenda.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.unpas.agenda.persistences.AppDatabase
import id.ac.unpas.agenda.persistences.BookDao
import id.ac.unpas.agenda.persistences.BookRequestDao
import id.ac.unpas.agenda.persistences.MemberDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: AppDatabase) : BookDao {
        return appDatabase.bookDao()
    }
    @Provides
    @Singleton
    fun provideMemberDao(appDatabase: AppDatabase) : MemberDao {
        return appDatabase.memberDao()
    }

    @Provides
    @Singleton
    fun provideRequestDao(appDatabase: AppDatabase) : BookRequestDao {
        return appDatabase.bookRequestDao()
    }
}