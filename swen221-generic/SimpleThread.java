class SimpleThread extends Thread {
	private String msg;
	SimpleThread(String m)
	{
	msg = m;
	}

	public void run(){
	for (int i=0;i<1000;i++){
	System.out.println(msg);
	}
	}

	public static void main(String args[]){
	Thread t1 = new SimpleThread("Hello");
	Thread t2 = new SimpleThread("Goodbye");
	t1.start();
	t2.start();
	}
}

