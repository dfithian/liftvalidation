package fithian.dirty

import net.liftweb.http.SHtml
import net.liftweb.http.js.{JsExp, JsCmd}
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JsCmds.Noop
import com.paytronix.utils.scala.result.{Result, Okay, Failed}

abstract class DirtyField[T](id: String, value: T)
{
    protected var currentValue: T = value
    protected def parse(s: String): Result[T]
    def isDirty(): Boolean = currentValue != value
    private val getVal: JsExp = JsRaw("""$('#""" + id + """').val() ? $('#""" + id + """').val() : ''""")
    def bind(s: String): JsCmd = {
        parse(s) match {
            case Okay(t) =>
                currentValue = t
            case _ =>
        }
        Noop
    }
    def exp(): JsExp = {
        SHtml.ajaxCall(getVal, (s: String) => bind(s))
    }
}
