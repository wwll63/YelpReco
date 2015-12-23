package FileControl;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;


public class test {

	private static final Pattern SPLITOR = Pattern.compile(",");
	 
	public static void main(String[] args) {
	    SparkConf sparkConf = new SparkConf()
	          .setAppName("K-Means")
	          .setMaster("local[2]");
	    JavaSparkContext sc = new JavaSparkContext(sparkConf);
	    JavaRDD<String> data = sc.textFile("data/test2.txt");
	    
	    JavaRDD<String> subData = data.map(s -> {
	    	List<String> splitData = Arrays.asList(SPLITOR.split(s));
	    	String values = splitData.get(0);
	    	for(int i=1;i<5;i++){
	    		values += SPLITOR+splitData.get(i);
	    	}
	    	return values;
	    });
	    
	    JavaRDD<String> info = data.map(s -> {
	    	List<String> splitData = Arrays.asList(SPLITOR.split(s));
	    	String values = splitData.get(6);
	    	for(int i=7;i<10;i++){
	    		values += SPLITOR+splitData.get(i);
	    	}
	    	return values;
	    });
	    
	    JavaRDD<Vector> parsedData = subData.map(s -> {
	    	double[] values = Arrays.asList(SPLITOR.split(s)).stream().mapToDouble(Double::parseDouble).toArray();
	        return Vectors.dense(values);
	    });
	    
	    //JavaRDD<Vector> parsedData = data.map(new Function<String, Vector>() {
		//	public Vector call(String s) throws Exception {
				// TODO Auto-generated method stub
		//		double[] values = Arrays.asList(SPACE.split(s)).stream()
		//				.mapToDouble(Double::parseDouble).toArray();
		//    	return Vectors.dense(values);
		//	}
		//});
	 
	    int numClusters = 3; //Ԥ���Ϊ3������
	    int numIterations = 20; //����20��
	    int runs = 10; //����10�Σ�ѡ�����Ž�
	    KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations, runs);
	 
	    //����������ݷֱ������Ǹ�����
	    print(parsedData.map(v -> v.toString()
	            + " belong to cluster :" + clusters.predict(v)).collect());
	    //print(parsedData.map(new Function<Vector, String>() {
		//	public String call(Vector v) throws Exception {
				// TODO Auto-generated method stub
		//    	return v.toString() + " belong to cluster :"
		//				+ clusters.predict(v);
		//	}
		//}).collect());
	    
	    //����cost
	    //double wssse = clusters.computeCost(parsedData.rdd());
	    //System.out.println("Within Set Sum of Squared Errors = " + wssse);
	 
	    //��ӡ�����ĵ�
	    System.out.println("Cluster centers:");
	    for (Vector center : clusters.clusterCenters()) {
	        System.out.println(" " + center);
	    }
	    //����һЩԤ��
	    //System.out.println("Prediction of (1.1, 2.1, 3.1): "
	    //      + clusters.predict(Vectors.dense(new double[]{1.1, 2.1, 3.1})));
	    //System.out.println("Prediction of (10.1, 9.1, 11.1): "
	    //      + clusters.predict(Vectors.dense(new double[]{10.1, 9.1, 11.1})));
	    //System.out.println("Prediction of (21.1, 17.1, 16.1): "
	    //      + clusters.predict(Vectors.dense(new double[]{21.1, 17.1, 16.1})));
	    
	}
	 
	public static <T> void print(Collection<T> c) {
	    for(T t : c) {
	        System.out.println(t.toString());
	    }
	}
	
	
}
