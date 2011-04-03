/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.process;

import java.util.ArrayList;

import listnet.data.Document;
import listnet.data.Sample;


/**
 * The Interface Normalizer.
 * 用来正规化文档或样本
 */
public interface Normalizer {

	/**
	 * 正规化一个文档.
	 *
	 * @param doc the doc
	 * @return 正规化之后的文档
	 */
	public abstract Document normalize(Document doc);
	
	/**
	 * 正规化一个样本..
	 *
	 * @param sample the sample
	 * @return 正规化之后的样本
	 */
	public abstract Sample normalize(Sample sample);
	public void debug();
	/**
	 * 获取正规化参数
	 *
	 * @return  正规化参数
	 */
	public abstract ArrayList<MaxMin> getNorParameters();
	
	/**
	 * The Class MaxMin.每个MaxMin实例保存这一维度的权重的最大值和最小值
	 */
	static class MaxMin{
		
		/** The max. */
		private double max;
		
		/** The min. */
		private double min;
		
		/**
		 * Instantiates a new max min.
		 *
		 * @param max the max
		 * @param min the min
		 */
		public MaxMin(double max, double min)
		{
			this.max = max;
			this.min = min;
		}

		/**
		 * Gets the max.
		 *
		 * @return the max
		 */
		public double getMax() {
			return max;
		}

		/**
		 * Gets the min.
		 *
		 * @return the min
		 */
		public double getMin() {
			return min;
		}

		/**
		 * Sets the max.
		 *
		 * @param max the new max
		 */
		public void setMax(double max) {
			this.max = max;
		}

		/**
		 * Sets the min.
		 *
		 * @param min the new min
		 */
		public void setMin(double min) {
			this.min = min;
		}
		
		
	}
}
