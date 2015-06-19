import java.util.*;

class PathCoverage {

	static int sumSmallest(List<Integer> v1, List<Integer> v2){
		int r=0;
		if(v1.size() <= v2.size()){
			for(int i=0;i!=v1.size();++i){
				r+=v1.get(i);
			}
		} else {
			for(int i=0;i!=v2.size();++i){
				r += v2.get(i);
			}
		}
		return r;
	}

	public static void main(String args[]){
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 = new ArrayList<Integer>();
		l1.add(1);
		l2.add(2);
		Integer r = sumSmallest(l1, l2);
		System.out.println(r);		
		System.out.println("-----");
	}
}