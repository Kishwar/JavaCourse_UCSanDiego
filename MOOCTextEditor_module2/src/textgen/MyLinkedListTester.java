/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		try {
			a = list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		a = longerList.remove(3);
		assertEquals("Remove: check size is correct ", LONG_LIST_LENGTH-1, longerList.size());
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		//get linked list size
        int nSize = longerList.size();
        
        //add DLList
        longerList.add(nSize, nSize);
        
		try {
			longerList.add(nSize+2, nSize+2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		//get linked list size
        int nSize = longerList.size();
        
        assertEquals("Size: check size is correct ", nSize, longerList.size());
        
        //remove LList
        longerList.remove(nSize-1);
        longerList.remove(nSize-2);
        
        assertEquals("Size: check size is correct ", nSize-2, longerList.size());
        
        //add LList
        longerList.add(nSize-1);
        assertEquals("Size: check size is correct ", nSize-1, longerList.size());
        
        longerList.add(nSize);
        assertEquals("Size: check size is correct ", nSize, longerList.size());
        
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		//get linked list size
        int nSize = longerList.size();
        
        longerList.add(nSize, nSize);
        assertEquals("AddAtIndex: check size is correct ", nSize+1, longerList.size());
        assertEquals("AddAtIndex: check value is correct ", (Integer)nSize, longerList.get(nSize));
	} 
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		//get linked list size
        int nSize = longerList.size();
        int nValue = longerList.get(nSize-1);
        
        assertEquals("AddAtIndex: check value is correct ", (Integer)nValue, longerList.get(nSize-1));
        
        //update value at this index
        longerList.set(nSize-1, nValue+1);
        
        assertEquals("AddAtIndex: check value is correct ", (Integer)(nValue+1), longerList.get(nSize-1));
	    
	}
	
	
	/** Loop to bigger index */
	@Test
	public void testLoopBig()
	{
		//get linked list size
        int nSize = longerList.size();
        
        for(int nLoop = nSize+1; nLoop < 255; nLoop++) {
        	try {
        		int a = list1.remove(nLoop);
        		fail("Check out of bounds");
        	}
        	catch (IndexOutOfBoundsException e) {
        	}
        	assertEquals("LoopBig: check size is correct ", nSize, longerList.size());
        }
        
        for(int nLoop = nSize+1; nLoop < 255; nLoop++) {
        	try {
        		list1.add(nLoop, nLoop);
        		fail("Check out of bounds");
        	}
        	catch (IndexOutOfBoundsException e) {
        	}
        	assertEquals("LoopBig: check size is correct ", nSize, longerList.size());
        }
	}
}
