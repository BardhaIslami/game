package com.assignment.app.lists;

import com.assignment.app.lists.LinkedList;
import com.assignment.app.lists.RecursiveLinkedList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RecursiveLinkedListTest {
	
	 @Test(expected = IllegalArgumentException.class)
	 
	 public void linkedListOfNone() {
	        RecursiveLinkedList<Object> recursiveLinkedList = new RecursiveLinkedList<>();
	        LinkedList<Object> linkedList = recursiveLinkedList.getFirst();
	    }

	    @Test
	    public void createLinkedListWithOneObj() {
	        Object obj = new Object();
	        RecursiveLinkedList<Object> recursiveLinkedList = new RecursiveLinkedList<>(obj);
	        LinkedList<Object> linkedList = recursiveLinkedList.getFirst();

	        assertEquals("RecursiveLinkedList with one object makes first link hold object.", obj, linkedList.getObj());
	        assertEquals("RecursiveLinkedList with one object makes first link same as next one.", linkedList, linkedList.getNext());
	    }

	    @Test
	    public void createLinkedListWithTwoObjs() {
	        Object obj1 = new Object();
	        Object obj2 = new Object();
	        RecursiveLinkedList<Object> recursiveLinkedList = new RecursiveLinkedList<>(obj1, obj2);
	        LinkedList<Object> linkedList = recursiveLinkedList.getFirst();

	        assertEquals("RecursiveLinkedList with two objects makes first link hold obj1.", obj1, linkedList.getObj());
	        assertEquals("RecursiveLinkedList with two objects makes second link hold obj2.", obj2, linkedList.getNext().getObj());
	        assertEquals("RecursiveLinkedList with two objects should be recursive.", linkedList, linkedList.getNext().getNext());
	    }

}
