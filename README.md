liftvalidation
==============

An input validation library for Lift

```scala
".formRepeater" #> fields.map { 
    case intField@IntValidator(id, _, _, _) => "#label" #> id & "#textbox" #> (SHtml.ajaxText("", (s: String) => Noop, "id" -> id) ++ intField)
    case decimalField@DecimalValidator(id, _, _, _) => "#label" #> id & "#textbox" #> (SHtml.ajaxText("", (s: String) => Noop, "id" -> id) ++ decimalField)
    case emailField@EmailValidator(id, _, _) => "#label" #> id & "#textbox" #> (SHtml.email("", (s: String) => Noop, "id" -> id) ++ emailField)
    case _ => "* *" #> NodeSeq.Empty
}
```