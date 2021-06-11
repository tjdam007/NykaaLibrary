package com.mjandroiddev.nykaalibrary

import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

/**
 * Session Related Unit tests
 * Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
 * */
class SessionUnitTest {
    @Test
    fun startSession1() {
        assertSame(
            20, when (0) {
                0 -> 20
                1 -> 30
                2 -> 45
                else -> 60
            }
        )
    }

    @Test
    fun startSession2() {
        assertSame(
            30, when (1) {
                0 -> 20
                1 -> 30
                2 -> 45
                else -> 60
            }
        )
    }

    @Test
    fun startSession3() {
        assertSame(
            45, when (2) {
                0 -> 20
                1 -> 30
                2 -> 45
                else -> 60
            }
        )
    }

    @Test
    fun startSession4() {
        assertSame(
            60, when (3) {
                0 -> 20
                1 -> 30
                2 -> 45
                else -> 60
            }
        )
    }
}