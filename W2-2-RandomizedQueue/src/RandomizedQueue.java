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

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add a null object");
		}
		itemQueue[size++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		int randItem = StdRandom.uniform(size);
		itemQueue[randItem - 1] = null;
		Item item = itemQueue[randItem];
		size--;
		return item;
	}

	// return (but do not remove) a random item
	public Item sample() {
		int randItem = StdRandom.uniform(size);
		Item item = itemQueue[randItem - 1];
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
			System.out.println(s);
			randQueue.enqueue(s);
		}
		
		
		for (String item : randQueue) {
			System.out.println("Here is item " + item);
		}
	}

	// Queue iterator implementation
	private class RandIterator implements Iterator<Item> {
		private int i = size;

		public boolean hasNext() {
			return i > 0;
		}

		public void remove() {
			/* not supported */
		}

		public Item next() {
			return itemQueue[++i];
		}
	}
}
