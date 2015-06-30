package com.github.tamaki.study.hajimeni.btree

/**
 * Created by nishiyama on 2015/06/25.
 */
trait Node {
  def size: Int
  def max: Int
  def min: Int
  def avg: Double
  def sum: Int
}


case class Branch(left: Node, value: Int, right: Node) extends Node {
  assert {
    val leftValue = left match {
      case leaf: Leaf => leaf.value
      case branch: Branch => branch.value
    }
    val rightValue = right match {
      case leaf: Leaf => leaf.value
      case branch: Branch => branch.value
    }
    leftValue < rightValue
  }

  override def size: Int = {
    left.size + right.size + 1
  }

  override def max: Int = List(left.max, value, right.max).max

  override def min: Int = List(left.min, value, right.min).min

  override def sum: Int = List(left.sum, value, right.sum).sum

  override def avg: Double = sum.toDouble / size.toDouble

}

object Branch {
  def apply(list: List[Int]): Branch = {
    assert(list.nonEmpty && list.size >= 3 && list.size % 2 == 1)

    val left = list.take(list.size / 2)
    val center = list.drop(left.size).head
    val right = list.takeRight(list.size / 2)

    new Branch(
      left match {
        case head :: Nil => Leaf(head)
        case xs => Branch(xs)
      },
      center,
      right match {
        case head :: Nil => Leaf(head)
        case xs => Branch(xs)
      })
  }
}

case class Leaf(value: Int) extends Node {
  override def size: Int = 1

  override def max: Int = value

  override def min: Int = value

  override def avg: Double = value.toDouble

  override def sum: Int = value
}

case class BTree(node: Node) {
  def size: Int = node.size
  def max: Int = node.max
  def min: Int = node.min
  def sum: Int = node.sum
  def avg: Double = node.avg
}

object BTree {
  def apply(list: List[Int]): BTree = {
    assert(list.size % 2 == 1)

    list.size match {
      case 1 => BTree(Leaf(list.head))
      case _ => BTree(Branch(list))
    }
  }
}

object Hoge extends App {
  val bTree1 = BTree(Leaf(1))
  val bTree2 = BTree(Branch(Leaf(1), 2, Leaf(3)))
  val bTree3 = BTree(Branch(Branch(Leaf(1), 2, Leaf(3)), 4, Branch(Leaf(5), 6, Leaf(7))))

  println(bTree1)
  println(bTree2)

  assert (bTree1.size == 1)
  assert (bTree2.size == 3)
  assert (bTree3.size == 7)
  assert (bTree3.max == 7)

  assert (bTree3.min == 1)
  assert (bTree3.sum == 28)
  assert (bTree3.avg == 4)


  val bTree = BTree(List(1, 2, 3, 4, 5, 6, 7))

  assert (bTree == bTree3)

  println(bTree)
  println(bTree3)
}