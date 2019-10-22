package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.pojos.Bin

data class BinsResponse(
    val isSuccessful: Boolean,
    val bins: List<Bin>
)