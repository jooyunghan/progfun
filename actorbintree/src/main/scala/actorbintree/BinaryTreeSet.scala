/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor._
import scala.collection.immutable.Queue

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef
    def id: Int
    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  /**
   * Request with identifier `id` to insert an element `elem` into the tree.
   * The actor at reference `requester` should be notified when this operation
   * is completed.
   */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /**
   * Request with identifier `id` to check whether an element `elem` is present
   * in the tree. The actor at reference `requester` should be notified when
   * this operation is completed.
   */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /**
   * Request with identifier `id` to remove the element `elem` from the tree.
   * The actor at reference `requester` should be notified when this operation
   * is completed.
   */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection*/
  case object GC

  /**
   * Holds the answer to the Contains request with identifier `id`.
   * `result` is true if and only if the element is present in the tree.
   */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply

  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

}

class BinaryTreeSet extends Actor {
  import BinaryTreeSet._
  import BinaryTreeNode._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  // optional
  //var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal(createRoot)

  // optional
  /** Accepts `Operation` and `GC` messages. */
  def normal(root: ActorRef): Receive = {
    case msg => root forward msg
  }

  // optional
  /**
   * Handles messages while garbage collection is performed.
   * `newRoot` is the root of the new binary tree where we want to copy
   * all non-removed elements into.
   */
  def garbageCollecting(newRoot: ActorRef): Receive = ???

}

object BinaryTreeNode {
  trait Position

  case object Left extends Position
  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode], elem, initiallyRemoved)
}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor {
  import BinaryTreeNode._
  import BinaryTreeSet._

  // optional
  def receive = normal(Map[Position, ActorRef](), initiallyRemoved)

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  def normal(subtrees: Map[Position, ActorRef], removed: Boolean): Receive = {
    case msg @ Contains(ref, id, value) =>
      if (elem == value)
        ref ! ContainsResult(id, !removed)
      else {
        val dir = if (value < elem) Left else Right
        if (subtrees.contains(dir))
          subtrees(dir) forward msg
        else
          ref ! ContainsResult(id, false)
      }
    case msg @ Insert(ref, id, value) =>
      if (elem == value) {
        ref ! OperationFinished(id)
        context.become(normal(subtrees, removed = false))
      } else {
        val dir = if (value < elem) Left else Right
        if (subtrees.contains(dir))
          subtrees(dir) forward msg
        else {
          ref ! OperationFinished(id)
          context.become(normal(subtrees + (dir -> context.actorOf(BinaryTreeNode.props(value, initiallyRemoved = false))),
              removed))
        }
      }
    case msg @ Remove(ref, id, value) =>
      if (elem == value) {
        ref ! OperationFinished(id)
        context.become(normal(subtrees, removed = true))
      } else {
        val dir = if (value < elem) Left else Right
        if (subtrees.contains(dir)) {
          subtrees(dir) forward msg
        } else {
          ref ! OperationFinished(id)
        }
      }
  }

  // optional
  /**
   * `expected` is the set of ActorRefs whose replies we are waiting for,
   * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
   */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = ???

}
