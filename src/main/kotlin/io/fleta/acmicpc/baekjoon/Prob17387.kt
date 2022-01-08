package io.fleta.acmicpc.baekjoon

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

typealias Point = Pair<Long, Long>

class Prob17387 {
    fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
        var stk = StringTokenizer(readLine())
        val (p1, p2) = listOf(stk.nextToken().toLong() to stk.nextToken().toLong(), stk.nextToken().toLong() to stk.nextToken().toLong())
        stk = StringTokenizer(readLine())
        val (p3, p4) = listOf(stk.nextToken().toLong() to stk.nextToken().toLong(), stk.nextToken().toLong() to stk.nextToken().toLong())

        println(if (validate(p1, p2, p3, p4)) 1 else 0)
    }

    fun ccw(p1: Point, p2: Point, p3: Point): Int {
        // a x b (cross product)
        val calculated = ((p2.first - p1.first) * (p3.second - p1.second)) - ((p3.first - p1.first) * (p2.second - p1.second))

        // ccw * ccw를 하면 long의 범위를 벗어날 수 있기 때문에 방향값만 체크하여 정제
        return when {
            calculated < 0L -> -1
            calculated == 0L -> 0
            else -> 1
        }
    }

    fun validate(p1: Point, p2: Point, p3: Point, p4: Point): Boolean {
        // has duplicated point
        val p123 = ccw(p1, p2, p3)
        val p124 = ccw(p1, p2, p4)
        val p341 = ccw(p3, p4, p1)
        val p342 = ccw(p3, p4, p2)

        var flag = false

        if (p123 * p124 == 0 && p341 * p342 == 0) {
            flag = true
            if (pointCompare(p1, p2, p3, p4)) {
                return true
            }
        }

        if (p123 * p124 <= 0 && p341 * p342 <= 0) {
            if (!flag) {
                 return true
            }
        }

        return false
    }

    fun pointCompare(p1: Point, p2: Point, p3: Point, p4: Point): Boolean {
        return min(p1.first, p2.first) <= max(p3.first, p4.first)
                && min(p1.second, p2.second) <= max(p3.second, p4.second)
                && min(p3.first, p4.first) <= max(p1.first, p2.first)
                && min(p3.second, p4.second) <= max(p1.second, p2.second)
    }
}

fun main() {
    Prob17387().main()
}