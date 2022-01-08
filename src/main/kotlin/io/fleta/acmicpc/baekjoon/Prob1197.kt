package io.fleta.acmicpc.baekjoon

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * minimum spanning tree with Kruskal algorithm
 */
class Prob1197 {
    fun main() {
        val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
        var stk = StringTokenizer(bufferedReader.readLine())
        val V = stk.nextToken().toInt()
        val E = stk.nextToken().toInt()
        val queue: PriorityQueue<Edge> = PriorityQueue()

        repeat(E) {
            stk = StringTokenizer(bufferedReader.readLine())
            val from = stk.nextToken().toInt() - 1
            val to = stk.nextToken().toInt() - 1
            val weight = stk.nextToken().toInt()
            queue.add(Edge(from, to, weight))
        }

        val root = IntArray(V) { i -> i }
        val rank = IntArray(V) { 0 }

        fun find(id: Int): Int {
            if (id == root[id]) return id
            root[id] = find(root[id])
            return root[id]
        }

        fun union(x: Int, y: Int) {
            val rootx = root[x]
            val rooty = root[y]
            if (rootx == rooty) return
            if (rank[rootx] < rank[rooty]) {
                root[rootx] = rooty
            } else {
                root[rooty] = rootx
                if (rank[rootx] == rank[rooty]) {
                    rank[rootx]++
                }
            }
        }

        var sum = 0
        while (!queue.isEmpty()) {
            val edge = queue.poll()
            val from = find(edge.from)
            val to = find(edge.to)
            if (from == to) continue
            union(from, to)
            sum += edge.weight
        }

        print(sum)
    }

    class Edge(val from: Int, val to: Int, val weight: Int): Comparable<Edge> {
        override fun compareTo(other: Edge): Int {
            return if (this.weight < other.weight) -1
            else if (this.weight > other.weight) 1
            else 0
        }
    }
}

fun main() {
    Prob1197().main()
}
