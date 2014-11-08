package com.fithian

import scala.util.matching.Regex
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

trait RegexValidator
{
    protected def regex: Regex
    def parse(value: String): Result[String] = {
        regex.findFirstIn(value) match {
            case Some(s) => Okay(s)
            case None => Failed("No match")
        }
    }
}
