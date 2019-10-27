package org.triniti.greensmart.utilities

import java.io.IOException

class ApiExceptions(message: String) : IOException(message)
class NoInternetException(message: String): IOException(message)
class MapCallBackException(message: String): IOException(message)