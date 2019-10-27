package org.triniti.greensmart.ui.home.bins

import org.triniti.greensmart.data.db.entities.User

interface OnCompletionListener{
    fun onCompletion(user: User)
}