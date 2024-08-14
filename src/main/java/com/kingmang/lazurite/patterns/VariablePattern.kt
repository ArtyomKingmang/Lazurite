package com.kingmang.lazurite.patterns

import java.io.Serializable

class VariablePattern : Pattern, Serializable  {


    private val variable: String?

    constructor(variable : String) {
        this.variable = variable
    }

    fun getVariable() : String? {
        return variable
    }

    override fun toString(): String {
        return variable!!
    }
}
