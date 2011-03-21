package listnet.process.util;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;

public class DotMultiply {
	/**
	 * Dot mutply.
	 *
	 * @param f1 the f1
	 * @param f2 the f2
	 * @return the double
	 * @throws Exception the exception
	 */
	public static double dotMutply(double[] f1, ArrayList<Double> f2) throws IllegalClassFormatException
	{
		if (f1.length != f2.size())
		{
			throw new IllegalClassFormatException("点积运算的两个向量长度不等！");
		}
		
		double sum = 0.0;
		for (int i=0; i<f1.length; i++)
		{
			sum += f1[i]*f2.get(i);
		}
		
		return sum;
	}
	public static double dotMutply(double[] f1, double[] f2) throws IllegalClassFormatException
	{
		if (f1.length != f2.length)
		{
			throw new IllegalClassFormatException("点积运算的两个向量长度不等！");
		}
		
		double sum = 0.0;
		for (int i=0; i<f1.length; i++)
		{
			sum += f1[i]*f2[i];
		}
		
		return sum;
	}
}
