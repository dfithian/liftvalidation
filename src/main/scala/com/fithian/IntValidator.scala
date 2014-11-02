package com.fithian

case class IntValidator(id: String, min: Int, max: Int, submitButton: Option[String])
    extends Validator[Int](id, submitButton)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be an integer between " + min + " and " + max
    // the script that we're going to run on blur
    val script: String = """
        function() {
            var value = $('#""" + id + """').val();
            if (value && (!$.isNumeric(value) || (value < """ + min + """ || value > """ + max + """ || value % 1 !== 0))) {
                """ + error + """
                """ + preventSubmit + """
            } else {
                """ + clear + """
                """ + allowSubmit + """
            }
        }"""
}
