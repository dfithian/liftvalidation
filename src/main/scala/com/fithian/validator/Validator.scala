package com.fithian

import net.liftweb.http.{SHtml, S, LiftRules}
import net.liftweb.http.js.JsCmds.{Script, Run, SetHtml, Noop}
import net.liftweb.http.js.{JsCmd, JsExp}
import net.liftweb.http.js.JE.{JsRaw, JsTrue}
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

abstract class Validator[T](id: String, submitButton: Option[String], errorId: Option[String])
{
    protected def parse(value: String): Result[T]
    protected def errorMessage: String
    private val liftId: String = errorId.getOrElse(LiftRules.noticesContainerId + id)
    private val (preventSubmit: String, allowSubmit: String) = submitButton match {
        case Some(buttonId) => ("""$('#""" + buttonId + """').click(function(evt) { evt.preventDefault(); } );
                                   $('#""" + buttonId + """').attr('disabled', 'disabled');""",
                                """$('#""" + buttonId + """').unbind('click');
                                   $('#""" + buttonId + """').removeAttr('disabled');""")
        case None => ("", "")
    }
    private def bindOnBlur(s: String): JsCmd = {
        parse(s) match {
            case Failed(error) if !(s == null || s == "") =>
                S.error(liftId, errorMessage + ". String was " + s)
                Run(preventSubmit)
            case _ =>
                Run(SetHtml(liftId, <span/>)) & Run(allowSubmit)
        }
    }
    private val getVal: JsExp = JsRaw("""$('#""" + id + """').val() ? $('#""" + id + """').val() : ''""")
    protected def exp(): JsExp = {
        SHtml.ajaxCall(getVal, (s: String) => bindOnBlur(s))
    }
}
