package com.assignment.app.lists;

public class RecursiveLinkedList<O> {

	private LinkedList<O> firstObj;
	private LinkedList<O> lastObj;

	// Build recursive link list with the specified objects

	public RecursiveLinkedList(final O... obj) {
		if (obj.length < 1)
			throw new IllegalArgumentException("Cannot build RecursiveLinkedList without elements.");
		this.firstObj = recursiveList(obj, 0);
		this.lastObj.setNext(this.firstObj);
	}

	// Get the first one

	public LinkedList<O> getFirst() {
		return firstObj;
	}

	// Build linear objects' linked list

	private LinkedList<O> recursiveList(final O[] obj, final int index) {
		if (index == obj.length - 1) {
			this.lastObj = new LinkedList<>(obj[index], null);
			return this.lastObj;
		}
		return new LinkedList<>(obj[index], recursiveList(obj, index + 1));
	}

}
