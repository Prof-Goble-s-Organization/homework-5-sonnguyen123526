package hw5;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class No6Tests {

	private COMP232ArrayHeap<Integer, String> heap;
	
	@Before
	public void setUp() {
		heap = new COMP232ArrayHeap<Integer, String>();
	}
	
	@Test
	public void testAddToEmptyHeap() {
		heap.add(1, "A");
		assertEquals("Incorrect size", 1, heap.size());
		
		assertEquals("Top of heap is not correct", "A", heap.peek());
	}
	
	@Test
	public void testAddLargestToOneElementHeap() {
		heap.add(1, "A");
		heap.add(2, "B");
		assertEquals("Incorrect size", 2, heap.size());
		assertEquals("Top of heap is not correct", "B", heap.remove());
		
		assertTrue("Heap property was not preserved by add", heap.checkHeapProperty());
		
		assertEquals("Top of heap not correct after remove", "A", heap.peek());
	}
	
	@Test
	public void testAddNonLargestToOneElementHeap() {
		heap.add(2, "B");
		heap.add(1, "A");
		assertEquals("Incorrect size", 2, heap.size());
		assertEquals("Top of heap is not correct", "B", heap.remove());
		assertTrue("Heap property was not preserved by add", heap.checkHeapProperty());
		assertEquals("Top of heap not correct after remove", "A", heap.peek());
	}
	
	@Test
	public void testAddLargestToGrowingHeap() {
		for (int i=0; i<100; i++) {
			heap.add(i, "" + i);
			assertEquals("Incorrect size", i+1, heap.size());
			assertEquals("Top of heap is not correct", "" + i, heap.peek());
			assertTrue("Heap property was not preserved by add", heap.checkHeapProperty());
		}
	}
	
	@Test
	public void testAddNotLargestToGrowingHeap() {
		for (int i=100; i>=0; i--) {
			heap.add(i, "" + i);
			assertEquals("Incorrect size", 100-i+1, heap.size());
			assertEquals("Top of heap is not correct", "100", heap.peek());
			assertTrue("Heap property was not preserved by add", heap.checkHeapProperty());
		}
	}
	
	@Test
	public void testRandomHeapBuiltCorrectly() {
		Random rnd = new Random();
		for (int i=0; i<1000; i++) {
			int k = rnd.nextInt(1000);
			heap.add(k, "" + k);
			assertTrue("Heap property was not preserved by add", heap.checkHeapProperty());
		}
	}
}