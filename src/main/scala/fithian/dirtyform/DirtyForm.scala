package fithian.dirty

import net.liftweb.util.CssSel
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb.http.SHtml
import net.liftweb.http.js.{JsExp, JsCmd}
import net.liftweb.http.js.JsCmds.{Noop, SetHtml}
import scala.xml.NodeSeq

class DirtyForm(targetId: String, saveFunc: () => JsCmd)
{
    var fields: List[DirtyField[_]] = List.empty[DirtyField[_]]
    val modalBind = ConfirmationModal(targetId, saveFunc)
    private def fieldsDirty(): JsCmd = {
        fields.exists(_.isDirty) match {
            case true => SetHtml(targetId, modalBind.open)
            case false => Noop
        }
    }
    def register(ids: List[DirtyField[_]]): Unit = fields = ids ::: fields
    def exp(): JsExp = {
        SHtml.ajaxInvoke(() => fieldsDirty)
    }
}