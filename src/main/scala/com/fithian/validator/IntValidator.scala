package com.fithian

import scala.xml.NodeSeq
import net.liftweb.util.CssSel
import com.paytronix.utils.scala.result.{tryCatchValue, Failed, Result}
import org.slf4j.Logger

case class IntValidator(id: String, min: Int, max: Int, submitButton: Option[String], logger: Logger)
    extends Validator[Int](id, submitButton, logger)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be an integer between " + min + " and " + max
    def parse(in: NodeSeq): Result[Int] = {
        for {
            i <- tryCatchValue(in.text.toInt)
            inBoundsOk <- Failed("Out of bounds") unless (i <= max && i >= min)
        } yield i
    }
}
