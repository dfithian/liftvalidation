package com.fithian

import scala.xml.{NodeSeq, Node}
import net.liftweb.http.js.JsCmds.{Script, Run}
import net.liftweb.http.js.{JsCmd}

abstract class Validator[T](id: String, submitButton: Option[String]) extends NodeSeq
{
    protected def errorMessage: String
    protected def script: String
    protected val liftId: String = "lift__noticesContainer__" + id
    // create an error box with the appropriate id
    protected val errorBox: NodeSeq = <span class="error alert alert-danger" id={liftId}></span>
    // fill the error box with the error message if it's empty
    protected def error: String = """if ($('#""" + liftId + """').is(':empty')) { $('#""" + liftId + """').append('""" + errorMessage + """'); }"""
    // clear the error box
    protected val clear: String = """$('#""" + liftId + """').empty();"""
    // attach the script to the attribute
    protected def onBlur: JsCmd = Run("""$('#""" + id + """').focusout(""" + script + """);""")
    // prevent the form submit if the id was supplied
    protected val (preventSubmit: String, allowSubmit: String) = submitButton match {
        case Some(buttonId) => ("""$('#""" + buttonId + """').click(function(evt) { evt.preventDefault(); } );
                                   $('#""" + buttonId + """').attr('disabled', 'disabled');""",
                                """$('#""" + buttonId + """').unbind('click');
                                   $('#""" + buttonId + """').removeAttr('disabled');""")
        case None => ("", "")
    }
    
    def theSeq: Seq[Node] = errorBox ++ Script(onBlur)
}
