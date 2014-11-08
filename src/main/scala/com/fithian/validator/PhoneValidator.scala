package com.fithian

import net.liftweb.http.js.JsExp
import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

class PhoneValidator private(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String])
    extends Validator[String](id, submitButton, errorId) with RegexValidator
{
    val regex: Regex = regexOpt.getOrElse("""^(?:(?:\+?1\s*(?:[.-]\s*)?)?(?:\(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\s*\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})(?:\s*(?:#|x\.?|ext\.?|extension)\s*(\d+))?$""".r)
    val errorMessage: String = "Please submit a valid phone number"
}

object PhoneValidator
{
    def apply(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String]): JsExp = {
        val validator = new PhoneValidator(id, regexOpt, submitButton, errorId)
        validator.exp
    }
}
