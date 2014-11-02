package com.fithian

case class DecimalValidator(id: String, min: BigDecimal, max: BigDecimal, submitButton: Option[String])
    extends Validator[BigDecimal](id, submitButton)
{
    if (min > max) throw new IllegalArgumentException("Min value must be less or equal to max")
    val errorMessage: String = "Value must be a decimal between " + min + " and " + max
    // the script that we're going to run on blur
    val script: String = """
        function() {
            var value = $('#""" + id + """').val();
            if (value && (!$.isNumeric(value) || (value < """ + min + """ || value > """ + max + """))) {
                """ + error + """
                """ + preventSubmit + """
            } else {
                """ + clear + """
                """ + allowSubmit + """
            }
        }"""
}
