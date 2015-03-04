import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size = 0;
	private Item[] itemQueue;
	private static final int CAPACITY = 10;

	// construct an empty randomized queue
	@SuppressWarnings("unchecked")
	public RandomizedQueue(int capacity) {
		itemQueue = (Item[]) new Object[capacity];
	}

	// is the queue empty?
	public boolean isEmpty() {
		return (size == 0);
	}

	// return the number of items on the queue
	public int size() {
		return this.size();
	}
	
	// Get current size
	public int getSize() {
		return this.size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add a null object");
		}
		itemQueue[size] = item;
		size++;
	}

	// remove and return a random item
	public Item dequeue(int numItems) {
		int randItem = StdRandom.uniform(numItems);
		if (randItem > 0) {
			randItem--;
		}		
		System.out.println("RandomizedQueue - dequeu() - numItems " + numItems + " randItem " + randItem + " ");
		Item item = itemQueue[randItem];
		itemQueue[randItem] = null;
		size--;
		return item;
	}

	// return (but do not remove) a random item
	public Item sample() {
		int randItem = StdRandom.uniform(size);
		System.out.println("randItem = " + randItem);
		Item item = itemQueue[randItem];
		return item;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandIterator();

	}

	// unit testing
	public static void main(String[] args) {
		RandomizedQueue<String> randQueue = new RandomizedQueue<String>(
				CAPACITY);

		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			System.out.println("Enqueued : " + s + " on element " + randQueue.getSize());
			randQueue.enqueue(s);
		}
		
		// Print a random item
		String sample = randQueue.sample();		
		System.out.println("The item " + sample + " was just sampled");

		// Iterator<String> i = randQueue.iterator();		
		for (String item : randQueue) {
			System.out.println("Here is item " + item);
		}

		// Dequeue one item
		String dequeued = randQueue.dequeue(randQueue.getSize());		
		System.out.println("The item " + dequeued + " was just dequeued");

		// Print the queue after the dequeue
		for (String item : randQueue) {
			System.out.println("Here is item " + item);
		}
		

	}

	// Queue iterator implementation
	private class RandIterator implements Iterator<Item> {
		private int i = 0;

		public boolean hasNext() {
			return i < CAPACITY;
		}

		public void remove() {
			/* not supported */
		}

		public Item next() {
			return itemQueue[i++];
		}
	}
}
