liftvalidation
==============

An ajax validation library for Lift

```scala
val fields: Seq[(String, NodeSeq)] = Seq(
    ("int", SHtml.text("", (s: String) => Noop, "id" -> "int", "onblur" -> IntValidator("int", 1, 22, Some("submitButton"), None).toJsCmd)),
    ("decimal", SHtml.text("", (s: String) => Noop, "id" -> "decimal", "onblur" -> DecimalValidator("decimal", BigDecimal(2.5), BigDecimal(3), Some("submitButton"), None).toJsCmd)),
    ("email", SHtml.email("", (s: String) => Noop, "id" -> "email", "onblur" -> EmailValidator("email", None, Some("submitButton"), None).toJsCmd)),
    ("phonenum", SHtml.text("", (s: String) => Noop, "id" -> "phonenum", "onblur" -> PhoneValidator("phonenum", None, Some("submitButton"), None).toJsCmd)),
    ("postalcode", SHtml.text("", (s: String) => Noop, "id" -> "postalcode", "onblur" -> PostalCodeValidator("postalcode", None, Some("submitButton"), None).toJsCmd))
)
def render = {
    ".formRepeater" #> fields.map { field =>
        "label *" #> field._1 &
        "#textbox" #> field._2 andThen
        // default id for errors is LiftRules.noticesContainerId + id
        "* *" #> ((in: NodeSeq) => <div id={LiftRules.noticesContainerId + field._1} class="lift-notices-container"></div> ++ in)
    }
}
```