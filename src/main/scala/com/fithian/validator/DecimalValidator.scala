package com.fithian

import net.liftweb.http.js.JsExp
import com.paytronix.utils.scala.result.{tryCatchValue, Failed, Result}

class DecimalValidator private(id: String, min: BigDecimal, max: BigDecimal, submitButton: Option[String], errorId: Option[String])
    extends Validator[BigDecimal](id, submitButton, errorId)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be a decimal between " + min + " and " + max
    def parse(value: String): Result[BigDecimal] = {
        for {
            i <- tryCatchValue(BigDecimal(value))
            inBoundsOk <- Failed("Out of bounds") unless (i <= max && i >= min)
        } yield i
    }
}

object DecimalValidator
{
    def apply(id: String, min: BigDecimal, max: BigDecimal, submitButton: Option[String], errorId: Option[String]): JsExp = {
        val validator = new DecimalValidator(id, min, max, submitButton, errorId)
        validator.exp
    }
}