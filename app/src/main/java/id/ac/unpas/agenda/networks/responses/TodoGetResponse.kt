package id.ac.unpas.agenda.networks.responses

import id.ac.unpas.agenda.models.Todo

data class TodoGetResponse(
    val data: List<Todo>
)
