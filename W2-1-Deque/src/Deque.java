
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int size = 0;

	// LinkedList<Item> Deque = new LinkedList<Item>();

	public Iterator<Item> iterator() {
		// return the iterator over items in order from front to end
		return new ListIterator();
	}

	public Deque() {
		System.out.println("Deque - Deque() ");
		// Construct an empty deque
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		// Is the deque empty?
		return (this.size == 0);
	}

	public int getSize() {
		// Returns the number of items in deque
		return this.size;
	}

	public void addFirst(Item item) {
		// remove and return the first item from the front of deque
		//first = new Node(); // instantiate Node and pass to local first
		if (isEmpty()) {
			first = new Node();
			first.item = item; // Assign item to local first
			first.next = null; // there is no next
			
			last = first;
			
		} else {
			Node oldFirst = first; // preserve first item
			first = new Node();
			first.item = item; // Assign item to local first
			first.next = oldFirst; // link first node to oldFirst
			first.next.previous = first; // points old firt to current first
		}
		this.size++;
	}

	public void addLast(Item item) {
		if (isEmpty()) {
			last = new Node();
			last.item = item; // adds the last/first item
			last.next = null;
			last.previous = null;

			first = last;
		} else {
			Node oldLast = last; // Preserve the current last
			last = new Node();
			last.item = item;
			last.next = null;
			last.previous = oldLast;
			last.previous.next = last;
		}
		this.size++;
	}

	public Item removeFirst() {
		Item item = null; // preserve the first item
		
		if(!isEmpty()) {
			Node oldFirst = first;
			item = first.item; // preserve the first item
			first = null;
			first = oldFirst.next; // make the first equal the next item
			if (!isEmpty()) {
				first.previous =  null;	
			}		
			this.size--;
		}
		
		return item;

	}

	public Item removeLast() {

		Item item = null;

		if (!isEmpty()) {
			
			item = last.item;
			last.previous.next = null;
			last = null;

			this.size--;

		}

		return item;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deque<String> deque = new Deque<String>();

		if (args[0] != null) {
			while (!StdIn.isEmpty()) {
				String s = StdIn.readString();
				if (args[0].equals("addFirst")) {
					System.out.println("addFirst : " + s);
					deque.addFirst(s);
				} else if (args[0].equals("addLast")) {
					System.out.println("addLast : " + s);
					deque.addLast(s);
				} else {
					System.out
							.println("Invalid Operation. Enter : addFirst, addLast or deque");
				}
			}
			// Print the loaded queue
			for (String item : deque) {
				System.out.println("The queue after load : " + item);
			}

			// Play with removal
			if (args[1].equals("removeFirst")) {
				String itemDequeued = deque.removeFirst();
				System.out.println("Item dequeued = " + itemDequeued);
			} else if (args[1].equals("removeLast")) {
				String itemDequeued = deque.removeLast();
				System.out.println("Item dequeued = " + itemDequeued);
			}

			// Print the after dequeue
			for (String item : deque) {
				System.out.println("The queue after deque : " + item);
			}

		} else {
			System.out.println("Usage: <class> addFirst, addLast or deque");
		}
	}

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() { /* not supported */
		};

		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

}
