package com.fithian

import net.liftweb.http.js.JsExp
import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

class EmailValidator private(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String])
    extends Validator[String](id, submitButton, errorId) with RegexValidator
{
    val regex: Regex = regexOpt.getOrElse("""^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$""".r)
    val errorMessage: String = "Please submit a valid email"
}

object EmailValidator
{
    def apply(id: String, regexOpt: Option[Regex], submitButton: Option[String], errorId: Option[String]): JsExp = {
        val validator = new EmailValidator(id, regexOpt, submitButton, errorId)
        validator.exp
    }
}
