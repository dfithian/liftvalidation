package com.fithian

import scala.xml.NodeSeq
import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}
import org.slf4j.Logger

case class EmailValidator(id: String, regexOpt: Option[Regex], submitButton: Option[String], logger: Logger)
    extends Validator[String](id, submitButton, logger)
{
    val regex: Regex = regexOpt.getOrElse("""/^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/""".r)
    val errorMessage: String = "Please submit a valid email"
    def parse(in: NodeSeq): Result[String] = {
        regex.findFirstIn(in.text) match {
            case Some(value) => Okay(value)
            case None => Failed("No match")
        }
    }
}
