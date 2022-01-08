package io.fleta.acmicpc.baekjoon

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

/**
 * topological sort
 */
fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    var stk = StringTokenizer(bufferedReader.readLine())
    val studentCount = stk.nextToken().toInt()
    val ruleCount = stk.nextToken().toInt()
    val students = initializeStudents(studentCount)

    repeat(ruleCount) {
        stk = StringTokenizer(bufferedReader.readLine())
        val a = stk.nextToken().toInt()
        val b = stk.nextToken().toInt()
        students[a - 1] isTallerThan students[b - 1]
    }

    printResult(makeSorted(students))
}

fun initializeStudents(studentCount: Int): MutableList<Student> {
    val students = mutableListOf<Student>()

    for (i in 1..studentCount) {
        students.add(Student(i))
    }

    return students
}

fun makeSorted(students: MutableList<Student>): MutableList<Student> {
    val queue: Queue<Student> = LinkedList()
    val sorted = mutableListOf<Student>()
    students.stream().forEach { student ->
        run {
            if (student.depth == 0) {
                queue.add(student)
            }
        }
    }

    while (!queue.isEmpty()) {
        val student = queue.remove()
        student.smallerStudents.forEach { smaller ->
            run {
                smaller.depth--
                if (smaller.depth == 0) {
                    queue.add(smaller)
                }
            }
        }
        sorted.add(student)
    }

    return sorted
}

fun printResult(students: MutableList<Student>) {
    print(students.joinToString(" ") { it.studentNum.toString() })
}


class Student(val studentNum: Int) {
    var depth = 0
    var smallerStudents = mutableListOf<Student>()

    infix fun isTallerThan(student: Student) {
        student.depth++
        smallerStudents.add(student)
    }
}