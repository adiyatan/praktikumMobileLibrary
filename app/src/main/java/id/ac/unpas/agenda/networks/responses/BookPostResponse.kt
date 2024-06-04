package id.ac.unpas.agenda.networks.responses

import id.ac.unpas.agenda.models.Book

data class BookPostResponse(
    val message: String,
    val success: Boolean,
    val data: Book?
)
