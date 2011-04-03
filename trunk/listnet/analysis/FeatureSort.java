/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.analysis;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import listnet.module.ListNetModule;
import listnet.module.Module;

/**
 * The Class FeatureSort.
 */
public class FeatureSort {

	/**
	 * Sort.
	 *
	 * @param m the m
	 */
	public static void sort(Module m)
	{
		double[] weights = m.getWeights();
		Windex[] wis = new Windex[weights.length];
		
		for (int i=0; i<weights.length; i++)
		{
			wis[i] = new Windex(i, weights[i]);
		}
		
		Arrays.sort(wis);
		
		for (int i=0; i<weights.length; i++)
		{
			System.out.println("i = " + wis[i].index + " weight = " + wis[i].weight);
		}
	}
	
	/**
	 * The Class Windex.
	 */
	private static class Windex implements Comparable<Windex>
	{
		
		/**
		 * Instantiates a new windex.
		 *
		 * @param i the i
		 * @param w the w
		 */
		public Windex(int i, double w) {
			this.index = i;
			this.weight = w;
		}
		
		/** The index. */
		int index;
		
		/** The weight. */
		double weight;
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Windex o) {
			double abs1 =  Math.abs(this.weight);
			double abs2 = Math.abs(o.weight);
			if( abs1 > abs2 )
				return 1;
			else if (abs1 < abs2)
				return -1;
			else 
				return 0;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o instanceof Windex)
			{
				Windex w = (Windex)o;
				if (this.weight == w.weight)
					return true;
			}
			return false;
		}
		
		@Override
		public int hashCode()
		{
			return new Double(this.weight).hashCode();
		}
	}
	
	
	/**
	 * 测试.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException
	{
		Module m = ListNetModule.getInstance(new File("msdata.listnet.module"));
		FeatureSort.sort(m);
	}
}
