public class Subset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final int CAPACITY = 10;
		int numItems = 0;
		RandomizedQueue<String> randomQueue = new RandomizedQueue<String>(
				CAPACITY);

		if (args[0] != null) {
			int numRandomElements = Integer.parseInt(args[0]);

			if (!StdIn.isEmpty()) {
				while (!StdIn.isEmpty()) {
					String s = StdIn.readString();
					randomQueue.enqueue(s);
					numItems++;
				}

				System.out.println("number of items : " + numItems);
				for (int i = 0; i <= numRandomElements; i++) {
					String dequeued = randomQueue.dequeue(numItems);
					System.out.println("Item dequeued was : " + dequeued);
				}
			}
		}
	}
}
