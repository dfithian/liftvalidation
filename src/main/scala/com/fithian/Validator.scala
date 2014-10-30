package com.fithian

import net.liftweb.util.CssSel
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetValById, SetHtml, Noop}
import net.liftweb.http.js.{JsCmd, JsExp}
import net.liftweb.http.{S, SHtml}
import scala.util.matching.Regex
import scala.xml.{Elem, NodeSeq, Text}

object Validator
{
    /**
     * Validate an email string. Compare against a regular expression:
     * 1. if it matches, do the success method
     * 2. if it doesn't match, show the error and reset the value
     */
    def apply[T](id: String,
              onSuccess: Option[T => Unit],
              exp: Option[Regex],
              originalValue: Option[T],
              errorMessage: Option[String]
    ): (T => JsCmd) = {
        (t: T) => exp.getOrElse("(?s).*)".r).findFirstMatchIn(t.toString) match {
            case Some(value) =>
                onSuccess.map(_(t))
                Noop
            case None =>
                errorMessage.map(error => S.error(error))
                SetHtml(id, Text(originalValue.map(_.toString).getOrElse("")))
        }
    }

    def apply[T](id: String, onSuccess: T => Unit, exp: Regex, originalValue: T, errorMessage: String): (T => JsCmd) =
        apply[T](id, Some(onSuccess), Some(exp), Some(originalValue), Some(errorMessage))

    def apply[T](id: String, onSuccess: T => Unit, originalValue: T, errorMessage: String): (T => JsCmd) =
        apply[T](id, Some(onSuccess), None, Some(originalValue), Some(errorMessage))
}