package id.ac.unpas.agenda.networks.responses

import id.ac.unpas.agenda.models.Todo

data class TodoPostResponse(
    val message: String,
    val success: Boolean,
    val data: Todo?
)
