liftvalidation
==============

A server-side input validation library for Lift

    ".repeater [onchange]" #> fields.map { (id: String, regex: Option[Regex], value: ValueCell[Option[String]]) =>
        "* *" #> SHtml.ajaxText("", Validator[String](id, None, regex, value.get, Some("Please submit a valid entry"))
    }