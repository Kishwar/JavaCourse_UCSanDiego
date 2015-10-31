package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		//Implement this method
		LLNode<E> current = new LLNode<E>(element);
		current.next = tail;
		current.prev = tail.prev;
		tail.prev.next = current;
		tail.prev = current;
		this.size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		//throw exception if these conditions are not set
		if ((this.size == 0) || (index >= size) || (index < 0))
			throw new IndexOutOfBoundsException("your message goes here");
		
		LLNode<E> current = new LLNode<E>(null);
		current = head.next;
		
		//starting from head.. lets scan
		for(int loop=0; loop<index; loop++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		//Implement this method
		//throw exception if these conditions are not set
		if ((index > size) || (index < 0) || (element == null))
			throw new IndexOutOfBoundsException("your message goes here");
		
		LLNode<E> current = new LLNode<E>(null);
		LLNode<E> newElement = new LLNode<E>(element);
		current = head.next;
		
		//starting from head.. lets scan
		for(int loop=0; loop<index; loop++) {
			current = current.next;
		}
		
		newElement.next = current;
		newElement.prev = current.prev;
		current.prev.next = newElement;
		current.prev = newElement;
		this.size++;
		
		return;
	}


	/** Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		//Implement this method
		//throw exception if these conditions are not set
		if ((this.size == 0) || (index >= size) || (index < 0))
			throw new IndexOutOfBoundsException("your message goes here");
		
		LLNode<E> current = new LLNode<E>(null);
		current = head.next;
		
		//starting from head.. lets scan
		for(int loop=0; loop<index; loop++) {
			current = current.next;
		}
		
		current.next.prev = current.prev;
		current.prev.next = current.next;
		this.size--;
		return current.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		//Implement this method
		//throw exception if these conditions are not set
		if ((this.size == 0) || (index >= size) || (index < 0) || (element == null))
			throw new IndexOutOfBoundsException("your message goes here");
		
		LLNode<E> current = new LLNode<E>(null);
		current = head.next;
		
		//starting from head.. lets scan
		for(int loop=0; loop<index; loop++) {
			current = current.next;
		}
		
		//don't change next/prev of DLList, Update only data
		current.data = element;
		
		return current.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
