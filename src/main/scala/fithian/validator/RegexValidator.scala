package fithian.validator

import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

/**
 * A trait to attach to any Validator requiring regular expression validation
 */
trait RegexValidator
{
    /**
     * The regular expression to be used
     */
    protected def regex: Regex
    /**
     * The validation function
     * @param value The string to evaluate
     * @return A com.paytronix.utils.scala.result.Result object (monad based on Either)
     */
    def parse(value: String): Result[String] = {
        regex.findFirstIn(value) match {
            case Some(s) => Okay(s)
            case None => Failed("No match")
        }
    }
}
