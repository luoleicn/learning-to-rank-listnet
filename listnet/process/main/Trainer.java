/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.process.main;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import listnet.data.Document;
import listnet.data.Sample;
import listnet.module.ListNetModule;
import listnet.module.Module;
import listnet.parameter.Parameters;
import listnet.process.DataReader;
import listnet.process.MSDataReader;
import listnet.process.MSDataReaderMem;
import listnet.process.MSNormalizer;
import listnet.process.Normalizer;
import listnet.process.util.DotMultiply;

/**
 * The Class Trainer.
 */
public class Trainer {

	/**
	 * Train.
	 * 注意每一百轮训练会保存一次模型，模型命名方式为时间_轮数。其中时间为long类型的表示
	 *
	 * @param f 训练文本文件
	 * @return 训练模型
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalClassFormatException 当计算点积时两个向量维度不一时
	 */
	public static Module train(File f) throws IOException, IllegalClassFormatException
	{
		System.out.println("Getting normaizer...");
		Normalizer nor = MSNormalizer.getNormalizer(f);
		nor.debug();
		DataReader reader = new MSDataReaderMem(f);
		int featureSize = reader.getNextSample().getDocuments().get(0).getFeatures().size();
		
		System.out.println("初始化权重（weights）...");
		double[] weights = new double[featureSize];
		for (int i=0; i<featureSize; i++)
		{
			weights[i] = Parameters.getWeightInit();
		}
		
		//训练
		for (int i=0; i<Parameters.getEpochNum(); i++)
		{
			
			reader.reset();
			Sample sample = null;
			//为了计算方差，保存oldweights
			double[] oldweights = Arrays.copyOf(weights, weights.length);
			
			while ((sample=reader.getNextSample()) != null)
			{
				sample = nor.normalize(sample);
				ArrayList<Document> doclist = sample.getDocuments();
				
				double[] Z = new double[doclist.size()];
				double[] ExpYList = new double[doclist.size()];
				double ExpYSum = 0.0;
				for (int doc=0; doc<doclist.size(); doc++)
				{
					Z[doc] = DotMultiply.dotMutply(weights, doclist.get(doc).getFeatures());
					ExpYList[doc] = Math.exp(doclist.get(doc).getRelevance());
					ExpYSum += ExpYList[doc];					
				}

				
				for (int v=0; v<featureSize; v++)
				{
					double deltaWP1 = 0.0;
					double deltaWP2 = 0.0;
					double deltaWP3 = 0.0;
					for (int doc=0; doc<doclist.size(); doc++)
					{
						deltaWP1 -= ((ExpYList[doc])*doclist.get(doc).getFeatures().get(v));
						
						double expz = Math.exp(Z[doc]);
						
						deltaWP2 += (expz*doclist.get(doc).getFeatures().get(v));
						
						deltaWP3 += expz;
						
					}
					
					double deltaW = deltaWP1/ExpYSum + deltaWP2/deltaWP3;
					weights[v] -= Parameters.getStep() * deltaW;
				}
			}
//			//每一百轮训练会保存一次模型
//			if ((i%100)==0)
//			{
//				Module m = ListNetModule.getInstance(weights, nor);
//				m.write(new File(new Date().getTime()+ "_" + i));
//			}
			//计算方差
			double sum = 0.0;
			for (int v=0; v<weights.length; v++)
			{
				sum += Math.pow(oldweights[v]-weights[v], 2);
			}
			System.out.println("finish training " + (i+1) + "/" + Parameters.getEpochNum() + " 方差是：" + sum );
			
			System.out.println("权重：");
			for (int v=0; v<weights.length; v++)
			{
				System.out.println("weight["+v+"] = " + weights[v]);
			}
		}
		return ListNetModule.getInstance(weights, nor);
	}
}
