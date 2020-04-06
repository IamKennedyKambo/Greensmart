package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.News

data class NewsResponse(val isSuccessful: Boolean, val news: List<News>, val message: String)
