package com.fithian

case class EmailValidator(id: String, regexOpt: Option[String], submitButton: Option[String])
    extends Validator[String](id, submitButton)
{

    val regex: String = regexOpt.getOrElse("""/^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/""")
    val errorMessage: String = "Please submit a valid email"
    // the script that we're going to run on blur
    val script: String = """
        function() {
            var value = $('#""" + id + """').val();
            var regex = """ + regex + """;
            if (value && (!(regex.test(value) ) ) ) {
                """ + error + """
                """ + preventSubmit + """
            } else {
                """ + clear + """
                """ + allowSubmit + """
            }
        }"""
}
