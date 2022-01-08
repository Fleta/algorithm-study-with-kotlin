package io.fleta.acmicpc.baekjoon

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

class Prob17387 {
    fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
        var stk = StringTokenizer(readLine())
        val (p1, p2) = listOf(stk.nextToken().toLong() to stk.nextToken().toLong(), stk.nextToken().toLong() to stk.nextToken().toLong())
        stk = StringTokenizer(readLine())
        val (p3, p4) = listOf(stk.nextToken().toLong() to stk.nextToken().toLong(), stk.nextToken().toLong() to stk.nextToken().toLong())

        println(if (validate(p1, p2, p3, p4)) 1 else 0)
    }

    fun ccw(p1: Pair<Long, Long>, p2: Pair<Long, Long>, p3: Pair<Long, Long>): Long {
        // a x b (cross product)
        val calculated = ((p2.first - p1.first) * (p3.second - p1.second)) - ((p3.first - p1.first) * (p2.second - p1.second))

        // TODO: 이렇게 안 바꿔주면 틀림. 왜인지는 아직 잘 모르겠음.
        return when {
            calculated < 0L -> -1
            calculated == 0L -> 0
            else -> 1
        }
    }

    fun validate(p1: Pair<Long, Long>, p2: Pair<Long, Long>, p3: Pair<Long, Long>, p4: Pair<Long, Long>): Boolean {
        // has duplicated point
        val p123 = ccw(p1, p2, p3)
        val p124 = ccw(p1, p2, p4)
        val p341 = ccw(p3, p4, p1)
        val p342 = ccw(p3, p4, p2)

        var flag = false

        if (p123 * p124 == 0L && p341 * p342 == 0L) {
            flag = true
            if (pointCompare(p1, p2, p3, p4)) {
                return true
            }
        }

        if (p123 * p124 <= 0L && p341 * p342 <= 0L) {
            if (!flag) {
                 return true
            }
        }

        return false
    }

    fun pointCompare(p1: Pair<Long, Long>, p2: Pair<Long, Long>, p3: Pair<Long, Long>, p4: Pair<Long, Long>): Boolean {
        return min(p1.first, p2.first) <= max(p3.first, p4.first)
                && min(p1.second, p2.second) <= max(p3.second, p4.second)
                && min(p3.first, p4.first) <= max(p1.first, p2.first)
                && min(p3.second, p4.second) <= max(p1.second, p2.second)
    }
}

fun main() {
    Prob17387().main()
}