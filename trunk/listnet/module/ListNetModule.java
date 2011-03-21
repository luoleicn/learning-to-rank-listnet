/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.module;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import listnet.process.MSNormalizer;
import listnet.process.Normalizer;
import listnet.process.Normalizer.MaxMin;

/**
 * The Class ListNetModule.
 */
public final class ListNetModule implements Module{

	/** The weights. */
	private double[] weights;
	
	/** The nor. */
	private Normalizer nor;
	
	/**
	 * Instantiates a new list net module.
	 */
	private ListNetModule(){}
	
	/**
	 * 从一个保存模型的文件中读取数据，并得到一个module对象.
	 *
	 * @param f 保存模型的文件
	 * @return single instance of Module
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Module getInstance(File f) throws IOException
	{
		DataInputStream reader = new DataInputStream(new FileInputStream(f));
		
		int featureSize = reader.readInt();
		
		double[] weights = new double[featureSize];
		ArrayList<MaxMin> mms = new ArrayList<Normalizer.MaxMin>();
		
		for (int i=0; i<featureSize; i++)
		{
			double max = reader.readDouble();
			double min = reader.readDouble();
			
			mms.add(new MaxMin(max, min));
		}
		
		for (int i=0; i<featureSize; i++)
		{
			weights[i] = reader.readDouble();
		}
		
		ListNetModule lnm = new ListNetModule();
		lnm.weights = weights;
		lnm.nor = new MSNormalizer(mms);
		
		return lnm;
	}
	
	/**
	 * 给定权值向量以及正规化对象，包装成一个Module对象.
	 *
	 * @param w 权值向量
	 * @param nor 正规化对象
	 * @return single instance of Module
	 */
	public static Module getInstance(double[] w, Normalizer nor)
	{
		ListNetModule m = new ListNetModule();
		m.weights = w;
		m.nor = nor;
		
		return m;
	}

	/* (non-Javadoc)
	 * @see listnet.module.Module#write(java.io.File)
	 */
	@Override
	public void write(File f) throws IOException {
		DataOutputStream writer = new DataOutputStream(new FileOutputStream(f));		
		ArrayList<MaxMin> maxmins = nor.getNorParameters();
		
		writer.writeInt(maxmins.size());
		
		for (MaxMin maxmin : maxmins)
		{
			writer.writeDouble(maxmin.getMax());
			writer.writeDouble(maxmin.getMin());
		}
		
		for (int i=0; i<weights.length; i++)
		{
			writer.writeDouble(weights[i]);
		}
		writer.close();
	}

	/* (non-Javadoc)
	 * @see listnet.module.Module#getWeights()
	 */
	@Override
	public double[] getWeights() {
		return this.weights;
	}

	/* (non-Javadoc)
	 * @see listnet.module.Module#getNormalizer()
	 */
	@Override
	public Normalizer getNormalizer() {
		return this.nor;
	}

}
