package id.ac.unpas.agenda.networks.responses

import id.ac.unpas.agenda.models.BookRequest

data class BookRequestGetResponse(
    val data: List<BookRequest>
)
