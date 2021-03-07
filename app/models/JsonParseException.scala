package models

final case class JsonParseException(private val message: String = "",
                                    private val cause: Throwable = None.orNull)
  extends Exception(message, cause)
