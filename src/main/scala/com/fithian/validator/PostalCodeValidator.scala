package com.fithian

import net.liftweb.http.js.JsExp
import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

class PostalCodeValidator private(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String])
    extends Validator[String](id, submitButton, errorId) with RegexValidator
{
    val regex: Regex = regexOpt.getOrElse("""^\d{5}(?:[-\s]\d{4})?$""".r)
    val errorMessage: String = "Please submit a valid postal code"
}

object PostalCodeValidator
{
    def apply(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String]): JsExp = {
        val validator = new PostalCodeValidator(id, regexOpt, submitButton, errorId)
        validator.exp
    }
}
