package fithian.dirty

import com.paytronix.utils.scala.result.{Result, Okay, Failed}

class StringField(id: String, value: String)
    extends DirtyField[String](id, value)
{
    def parse(s: String): Result[String] = Okay(s)
}
