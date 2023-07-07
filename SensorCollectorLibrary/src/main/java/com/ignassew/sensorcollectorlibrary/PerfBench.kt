package com.ignassew.sensorcollectorlibrary

import android.os.SystemClock
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.sqrt

internal class PerfBench() {
    private fun benchmark(): String {
        try {
            val uptimeMillis = SystemClock.uptimeMillis()
            var i2 = 1
            var i3 = 0
            var i4 = 0
            while (true) {
                if (i2 >= 1000000) {
                    break
                }
                if (((4508713 % i2) * 11) % i2 == 0) {
                    i3++
                }
                if (i2 % 100 == 0 && SystemClock.uptimeMillis() - uptimeMillis > 2) {
                    break
                }
                i4++
                i2++
            }
            val i5 = i4 / 100

            val uptimeMillis2 = SystemClock.uptimeMillis()
            var f = 33.34f
            var i6 = 0
            var i7 = 0
            for (i8 in 1 until 1000000) {
                f += i8
                if ((19.239f * f) / 3.56f < 10000.0f) {
                    i6++
                }
                if (i8 % 100 == 0 && SystemClock.uptimeMillis() - uptimeMillis2 > 2) {
                    break
                }
                i7++
            }
            val i9 = i7 / 100

            val uptimeMillis3 = SystemClock.uptimeMillis()
            var i10 = 0
            var i11 = 0
            var d = 0.0
            while (d < 1000000.0) {
                if (sqrt(d) > 30.0) {
                    i10++
                }
                if (d.toInt() % 100 == 0 && SystemClock.uptimeMillis() - uptimeMillis3 > 2) {
                    break
                }
                i11++
                d += 1.0
            }
            val i12 = i10
            val i13 = i11 / 100

            val uptimeMillis4 = SystemClock.uptimeMillis()
            var i14 = 1
            var i15 = 0
            var i16 = 0
            val i = 1000000
            while (i14 < i) {
                if (
                    acos(i14.toDouble() / i.toDouble())
                    + asin(i14.toDouble() / i.toDouble())
                    + atan(i14.toDouble() / 1000000.toDouble())
                    > 1.5
                ) {
                    i15++
                }
                if (i14 % 100 == 0 && SystemClock.uptimeMillis() - uptimeMillis4 > 2) {
                    break
                }
                i16++
                i14++
            }
            val i17 = i16 / 100

            val uptimeMillis5 = SystemClock.uptimeMillis()
            var i18 = 0
            var i19 = 1
            while (i19 < 1000000 && SystemClock.uptimeMillis() - uptimeMillis5 <= 2) {
                i18++
                i19++
            }

            return "$i3,$i5,$i6,$i9,$i12,$i13,$i15,$i17,$i18"
        } catch (e: Exception) {
            println("ERROR(perf bench): $e")
            return "-1,-1,-1,-1,-1,-1,-1,-1,-1"
        }
    }

    fun benchmarkMultiple(n: Int): List<String> {
        val l = arrayListOf<String>();
        for (i in 0..n) {
            l.add(benchmark())
        }
        return l
    }
}