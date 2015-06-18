class CharBuffer {
	private char[] buffer;
	private int length=0;

	public CharBuffer(int max){
		buffer = new char[max];
	}

	public CharBuffer(char[] buffer){
		char[] tmp = new char[buffer.length];
		System.arraycopy(buffer,0,tmp,0,buffer.length);
		this.buffer = tmp;
		this.length = buffer.length;
	}

	public void append(char c){

	}

	public char charAt(int index){
		if(index < 0 || index >= length){
			throw new IndexOutOfBoundsException();
		}
		return buffer[index];
	}

	public void set(int index, char c){
		buffer[index] = c;
	}

	public int length(){
		return 0;
	}

	public String toString(){
		return new String(buffer,0,length);
	}

}


public class Q1_c{
	
	public static void main(String args[]){
		char[] array = {'H','e','l','l','o'};
		CharBuffer left = new CharBuffer(array);
		CharBuffer right = new CharBuffer(array);
		right.set(0,'h');
		System.out.println(left.toString() + " => " + right.toString());
	}
	
}



