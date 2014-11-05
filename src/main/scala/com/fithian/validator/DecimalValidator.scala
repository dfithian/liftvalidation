package com.fithian

import scala.xml.NodeSeq
import com.paytronix.utils.scala.result.{tryCatchValue, Failed, Result}
import org.slf4j.Logger

case class DecimalValidator(id: String, min: BigDecimal, max: BigDecimal, submitButton: Option[String], logger: Logger)
    extends Validator[BigDecimal](id, submitButton, logger)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be a decimal between " + min + " and " + max
    def parse(in: NodeSeq): Result[BigDecimal] = {
        (for {
            i <- tryCatchValue(BigDecimal(in.text))
            inBoundsOk <- Failed("Out of bounds") unless (i <= max && i >= min)
        } yield i)
    }
}
