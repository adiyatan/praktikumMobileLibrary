package id.ac.unpas.agenda.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.ac.unpas.agenda.networks.TodoApi
import id.ac.unpas.agenda.persistences.TodoDao
import id.ac.unpas.agenda.repositories.TodoRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideTodoRepository(todoDao: TodoDao, todoApi: TodoApi): TodoRepository {
        return TodoRepository(todoApi, todoDao)
    }
}