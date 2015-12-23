package FileControl;
import java.util.ArrayList;
import java.util.Hashtable;


public class test2 {
	public static void main(String[] args){
//		Hashtable<Integer, String> ht = new Hashtable<Integer, String>();
//		ht.put(1, "111111");
//		ht.put(2, "222222");
//		ht.put(1, "211111");
//		ht.put(1, "311111");
//		System.out.println(ht.get(1));
		ArrayList<ArrayList<String>> als = new ArrayList<ArrayList<String>> ();
		for(int i=0;i<3;i++){
			ArrayList<String> a = new ArrayList<String>();
			als.add(a);
		}
		als.get(0).add("000000");
		als.get(0).add("100000");
		als.get(1).add("111111");
		als.get(1).add("211111");
		als.get(1).add("311111");
		for(int i=0;i<3;i++){
			System.out.println(als.get(1).get(i));
		}
		System.out.println(als.get(1).contains("111111"));
		als.get(1).clear();
		System.out.println(als.get(1).contains("111111"));
		String s = "12  23 34 45";
		double d = Double.parseDouble("12");
		System.out.println(s.split(" ")[0]+d);
		System.out.println(s.split(" ")[1]+d);
	}
}
