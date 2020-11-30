package com.assignment.app.lists;

public class LinkedList<O> {

	private O obj;
	private LinkedList<O> next;

	public LinkedList(O obj, LinkedList<O> next) {
	        this.obj = obj;
	        this.next = next;
	    }

	public O getObj() {
		return obj;
	}

	public LinkedList<O> getNext() {
		return next;
	}

	public void setNext(LinkedList<O> next) {
		this.next = next;
	}

}
