package fithian.validator

import net.liftweb.http.js.JsExp
import com.paytronix.utils.scala.result.{tryCatchValue, Failed, Result}

/**
 * A Validator class for integer values
 */
class IntValidator private(id: String, min: Int, max: Int, submitButton: Option[String], errorId: Option[String])
    extends Validator[Int](id, submitButton, errorId)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be an integer between " + min + " and " + max
    def parse(value: String): Result[Int] = {
        for {
            i <- tryCatchValue(value.toInt)
            inBoundsOk <- Failed("Out of bounds") unless (i <= max && i >= min)
        } yield i
    }
}

object IntValidator
{
    def apply(id: String, min: Int, max: Int, submitButton: Option[String], errorId: Option[String]): JsExp = {
        val validator = new IntValidator(id, min, max, submitButton, errorId)
        validator.exp
    }
}
