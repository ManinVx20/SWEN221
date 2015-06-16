class SimpleThread2 extends Thread {
	private static int v = 0;

	public void run() {
		SimpleThread2.v = SimpleThread2.v + 1;
		System.out.println("v = "+SimpleThread2.v);
	}

	public static void main(String args[]){
		Thread t1 = new SimpleThread2();
		Thread t2 = new SimpleThread2();
		Thread t3 = new SimpleThread2();

		t1.start();
		t2.start();
		t3.start();
	}
}