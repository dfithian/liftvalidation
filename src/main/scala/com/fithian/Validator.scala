package com.fithian

import net.liftweb.util.CssSel
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetValById, SetHtml, Noop}
import net.liftweb.http.js.{JsCmd, JsExp}
import net.liftweb.http.S
import scala.util.matching.Regex
import scala.xml.{Elem, NodeSeq, Text}

object Validator
{
    /**
     * Validate an email string. Compare against a regular expression:
     * 1. if it matches, do the success method
     * 2. if it doesn't match, show the error and reset the value
     */
    def apply(id: String,
              onSuccess: Option[String => Unit],
              exp: Option[Regex],
              originalValue: Option[String],
              errorMessage: Option[String]
    ): (String => JsCmd) = {
        (s: String) => exp.getOrElse("(?s).*)".r).findFirstMatchIn(s) match {
            case Some(value) =>
                onSuccess.map(_(s))
                Noop
            case None =>
                errorMessage.map(error => S.error(error))
                SetHtml(id, Text(originalValue.getOrElse("")))
        }
    }
}