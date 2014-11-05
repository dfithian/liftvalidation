package com.fithian

import scala.xml.{NodeSeq, Node}
import net.liftweb.util.CssSel
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb.http.{SHtml, S, MemoizeTransform}
import net.liftweb.http.js.JsCmds.{Script, Run, SetHtml}
import net.liftweb.http.js.{JsCmd}
import net.liftweb.http.js.JE.AnonFunc
import com.paytronix.utils.scala.result.{Result, Okay, Failed}
import org.slf4j.Logger

//TODO: bind these functions as ajax calls back to the server! then you can do S.error instead of inserting HTML

abstract class Validator[T](id: String, submitButton: Option[String], logger: Logger)
{
    protected def parse(in: NodeSeq): Result[T]
    protected def errorMessage: String
    private val (preventSubmit: String, allowSubmit: String) = submitButton match {
        case Some(buttonId) => ("""$('#""" + buttonId + """').click(function(evt) { evt.preventDefault(); } );
                                   $('#""" + buttonId + """').attr('disabled', 'disabled');""",
                                """$('#""" + buttonId + """').unbind('click');
                                   $('#""" + buttonId + """').removeAttr('disabled');""")
        case None => ("", "")
    }
    private def bindOnBlur: MemoizeTransform = SHtml.memoize {
        "*" #> ((in: NodeSeq) => parse(in) match {
            case Okay(_) =>
                S.clearCurrentNotices
                Script(Run(allowSubmit))
            case Failed(error) =>
                S.error(id, errorMessage)
                Script(Run(preventSubmit))
        })
    }
    // prevent the form submit if the id was supplied
    def apply(): CssSel = {
        "* *" #> ((in: NodeSeq) => {logger.info(in.toString); in}) &
        ("#" + id + " [value]") #> {logger.info("hello"); bindOnBlur} &
        "* [onblur]" #> {logger.info("goodbye"); Script(AnonFunc("", SHtml.ajaxInvoke(() => SetHtml(id, bindOnBlur.applyAgain))._2.cmd).cmd)}
    }
}