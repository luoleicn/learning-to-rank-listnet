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

import listnet.data.Document;
import listnet.data.Sample;
import listnet.module.ListNetModule;
import listnet.module.Module;
import listnet.parameter.Parameters;
import listnet.process.DataReader;
import listnet.process.MSDataReader;
import listnet.process.MSNormalizer;
import listnet.process.Normalizer;
import listnet.process.util.DotMultiply;

/**
 * The Class Trainer.
 */
public class Trainer {

	/**
	 * Train.
	 *
	 * @param f 训练文本文件
	 * @return 训练模型
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalClassFormatException 当计算点积时两个向量维度不一时
	 */
	public static Module train(File f) throws IOException, IllegalClassFormatException
	{
		Normalizer nor = MSNormalizer.getNormalizer(f);
		DataReader reader = new MSDataReader(f);
		int featureSize = reader.getNextSample().getDocuments().get(0).getFeatures().size();
		
		double[] weights = new double[featureSize];
		//初始化权重（weights）
		for (int i=0; i<featureSize; i++)
		{
			weights[i] = Parameters.getWeightInit();
		}
		
		//训练
		for (int i=0; i<Parameters.getEpochNum(); i++)
		{
			reader.reset();
			Sample sample = null;
			while ((sample=reader.getNextSample()) != null)
			{
				nor.normalize(sample);
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
		}
		return ListNetModule.getInstance(weights, nor);
	}
}
