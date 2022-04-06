package com.wxj

import scala.annotation.tailrec

object ScalaTest {
  // 尾递归注解，scala编译器会自动进行优化
  @tailrec
  def tailSum(n: Int, sum: Int): Int = {
    if (n == 0) {
      return sum
    }
    tailSum(n - 1, n + sum)
  }

  def main(args: Array[String]): Unit = {
    println(tailSum(3, 0))
    println(tailSum(1000000, 0))
  }
}
