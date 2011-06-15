/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.parameter;

/**
 * The Class Parameters.
 */
public class Parameters {

//	/** 全部文档数量. */
//	private static int docSize;
//	
//	/** 全部样本数量. */
//	private static int sampleSize;
	
	/** 训练的轮数. */
	private static int EpochNum = 4000;
	
	/** 权重初始值. */
	private static final double WeightInit = 0.0;
	
	/** 梯度下降法的学习步长. */
	private static final double Step = 0.0025;
	
	/** 训练中没多少轮保存一次. */
	private static int save = -1;
	/**
	 * Instantiates a new parameters.
	 */
	private Parameters(){}
	
	/**
	 * Gets the epoch num.
	 *
	 * @return the epoch num
	 */
	public static int getEpochNum() {
		return EpochNum;
	}

	
	/**
	 * Sets the epochnum.
	 *
	 * @param n the new epochnum
	 */
	public static void setEpochnum(int n) {
		EpochNum = n;
	}

	/**
	 * Gets the weight init.
	 *
	 * @return the weight init
	 */
	public static double getWeightInit() {
		return WeightInit;
	}


	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public static double getStep() {
		return Step;
	}

	/**
	 * Gets the save.
	 *
	 * @return the save
	 */
	public static int getSave() {
		return save;
	}

	/**
	 * Sets the save.
	 *
	 * @param save the new save
	 */
	public static void setSave(int save) {
		Parameters.save = save;
	}
	
	
}
