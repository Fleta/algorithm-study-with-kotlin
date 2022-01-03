package io.fleta.acmicpc.baekjoon

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Queue
import java.util.LinkedList

import kotlin.math.max

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val testCaseCount = bufferedReader.readLine().toInt()

    for (i in 1..testCaseCount) {
        // 0: node 갯수, 1: rule 갯수
        val counts = bufferedReader.readLine().split(" ").map { it.toInt() }.toIntArray()

        val constructTimes = bufferedReader.readLine().split(" ").map { it.toInt() }.toIntArray()
        val tuples = mutableListOf<Pair<Int, Int>>()

        for (j in 1..counts[1]) {
            val line = bufferedReader.readLine()
            tuples.add(Pair(line.substringBefore(" ").trim().toInt(), line.substringAfter(" ").trim().toInt()))
        }

        val endNode = bufferedReader.readLine().toInt()
        println(sort(drawTree(constructTimes, tuples)).get(endNode - 1).totalStructTime)
    }
}

fun drawTree(constructTimes: IntArray, tuples: MutableList<Pair<Int, Int>>): MutableList<Node> {
    val nodes = mutableListOf<Node>()

    constructTimes.forEach { time -> nodes.add(Node(time)) }
    tuples.forEach {tuple -> nodes.get(tuple.first - 1) isParentOf nodes.get(tuple.second - 1)}

    return nodes
}

fun sort(nodes: MutableList<Node>): MutableList<Node> {
    val queue: Queue<Node> = LinkedList()
    nodes.stream().forEach { node ->
        run {
            if (node.depth == 0) {
                queue.add(node)
            }
        }
    }

    while (!queue.isEmpty()) {
        val node = queue.remove()

        node.children.stream().forEach { child ->
            run {
                child.depth--
                if (child.depth == 0) {
                    queue.add(child)
                }
                child.totalStructTime = max(child.totalStructTime, node.totalStructTime + child.structTime)
            }
        }
    }

    return nodes
}

class Node(val structTime: Int) {
    var depth = 0
    var totalStructTime = structTime
    val children = mutableListOf<Node>()

    infix fun isParentOf(child: Node) {
        children.add(child)
        child.depth += 1
    }
}