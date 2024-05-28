package id.ac.unpas.agenda.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.ac.unpas.agenda.networks.BookApi
import id.ac.unpas.agenda.networks.BookRequestApi
import id.ac.unpas.agenda.persistences.BookDao
import id.ac.unpas.agenda.repositories.BookRepository
import id.ac.unpas.agenda.networks.MemberApi
import id.ac.unpas.agenda.persistences.BookRequestDao
import id.ac.unpas.agenda.persistences.MemberDao
import id.ac.unpas.agenda.repositories.BookRequestRepository
import id.ac.unpas.agenda.repositories.MemberRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideBookRepository(todoDao: BookDao, todoApi: BookApi): BookRepository {
        return BookRepository(todoApi, todoDao)
    }
    @Provides
    @ViewModelScoped
    fun provideMemberRepository(todoDao: MemberDao, todoApi: MemberApi): MemberRepository {
        return MemberRepository(todoApi, todoDao)
    }

    @Provides
    @ViewModelScoped
    fun provideBookRequestRepository(todoDao: BookRequestDao, todoApi: BookRequestApi): BookRequestRepository {
        return BookRequestRepository(todoApi, todoDao)
    }
}