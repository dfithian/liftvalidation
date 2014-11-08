package fithian.dirty

import scala.xml.NodeSeq
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JsCmds.Script

// a modal popup to confirm navigation away from current location
class ConfirmationModal(id: String, script: () => JsCmd)
{
    val formId = "form_" + id
    def open: NodeSeq = {
        <div class="modal fade" id={id} tabindex="-1" role="dialog" aria-labelledby="Title" aria-hidden="true">
            <div class="modal-dialog">
                <form class="modal-content col-md-6 col-md-offset-3 form-horizontal" id={formId} role="form" method="post" onsubmit="return false">
                    <div class="modal-header">
                        <button type="button" class="cancel" data-dismiss="modal" aria-hidden="true">x</button>
                        <h4 class="modal-title">You have unsaved changes. Are you sure you want to continue?</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="col-lg-12 formDescription">
                                <span class="modal-description">You have unsaved changes. Do you want to save?</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="cancel" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-default noSaveButton" data-dismiss="modal">Don't Save</button>
                        <button type="submit" class="btn btn-primary saveButton">Save</button>
                    </div>
                </form>
            </div>
        </div>
    }
    def close: NodeSeq = {
        Script(JsRaw("""$('#""" + id + """').off('hidden.bs.modal').on('hidden.bs.modal', function() { """.stripMargin + SHtml.ajaxInvoke(script)._2.toJsCmd + """ });""").cmd)
    }
}

object ConfirmationModal
{
    def apply(id: String, script: () => JsCmd) = new ConfirmationModal(id, script)
}