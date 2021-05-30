package com.rsschool.android2021

interface Communicator {
    fun sendNumber(max: Int, min: Int)
    fun sendResult(result: Int)
}