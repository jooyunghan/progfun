package simulations

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CircuitSuite extends CircuitSimulator with FunSuite {
  val InverterDelay = 1
  val AndGateDelay = 3
  val OrGateDelay = 5
  
  test("andGate example") {
	  val in1, in2, out = new Wire
			  andGate(in1, in2, out)
	  in1.setSignal(false)
	  in2.setSignal(false)
	  run
	  
	  assert(out.getSignal === false, "and 1")
	  
	  in1.setSignal(true)
	  run
	  
	  assert(out.getSignal === false, "and 2")
	  
	  in2.setSignal(true)
	  run
	  
	  assert(out.getSignal === true, "and 3")
  }
  test("orGate example") {
	  val in1, in2, out = new Wire
			  orGate(in1, in2, out)
	  in1.setSignal(false)
	  in2.setSignal(false)
	  run
	  
	  assert(out.getSignal === false, "or 1")
	  
	  in1.setSignal(true)
	  run
	  
	  assert(out.getSignal === true, "and 2")
	  
	  in2.setSignal(true)
	  run
	  
	  assert(out.getSignal === true, "and 3")
  }
  test("orGate2 example") {
	  val in1, in2, out = new Wire
			  orGate2(in1, in2, out)
	  in1.setSignal(false)
	  in2.setSignal(false)
	  run
	  
	  assert(out.getSignal === false, "or 1")
	  
	  in1.setSignal(true)
	  run
	  
	  assert(out.getSignal === true, "and 2")
	  
	  in2.setSignal(true)
	  run
	  
	  assert(out.getSignal === true, "and 3")
  }

  test("demux with no control") {
    val in, out = new Wire
    demux(in, List(), List(out))
    in.setSignal(false)
    run
    assert(out.getSignal == false)
    
    in.setSignal(true)
    run
    assert(out.getSignal == true)
  }
  test("demux with 1 control") {
    val in, c, out1, out2 = new Wire
    demux(in, List(c), List(out1, out2))
    in.setSignal(false)
    c.setSignal(false)
    run
    assert(out1.getSignal == false)
    assert(out2.getSignal == false)
    
    in.setSignal(true)
    c.setSignal(true)
    run
    assert(out1.getSignal == true)
    assert(out2.getSignal == false)
    
    c.setSignal(false)
    run
    assert(out1.getSignal == false)
    assert(out2.getSignal == true)
    
  }
}