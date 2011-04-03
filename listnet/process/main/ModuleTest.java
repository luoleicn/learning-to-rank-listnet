/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.process.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;

import listnet.data.Document;
import listnet.data.MSDocument;
import listnet.module.ListNetModule;
import listnet.module.Module;
import listnet.process.util.DotMultiply;

/**
 * The Class ModuleTest.
 */
public class ModuleTest {

	/** The module. */
	private final Module module;
	
	/**
	 * Instantiates a new module test.
	 *
	 * @param module the module
	 */
	public ModuleTest(Module module) 
	{
		this.module = module;
	}
	
	/**
	 * Test.
	 *
	 * @param weights the weights
	 * @return the double
	 * @throws IllegalClassFormatException the illegal class format exception
	 */
	public double test(double[] weights) throws IllegalClassFormatException
	{
		return DotMultiply.dotMutply(module.getWeights(), weights);
	}
	
	/**
	 * Test.
	 *
	 * @param weights the weights
	 * @return the double
	 * @throws IllegalClassFormatException the illegal class format exception
	 */
	public double test(ArrayList<Double> weights) throws IllegalClassFormatException
	{
		return DotMultiply.dotMutply(module.getWeights(), weights);
	}
	
	public static void main(String[] args) throws IOException, IllegalClassFormatException
	{
		Module m = ListNetModule.getInstance(new File("msdata.listnet.module"));
		ModuleTest tester = new ModuleTest(m);
		BufferedReader reader = new BufferedReader(new FileReader("/home/luolei/dmc/download_data/data/fold1/test.txt"));
		
		String line = null;
		while ((line = reader.readLine()) != null)
			{
				Document doc = MSDocument.parseDoc(line);
				doc = m.getNormalizer().normalize(doc);
				
				System.out.println("res = " + tester.test(doc.getFeatures()) + " ans = " + doc.getRelevance());
			}
	}
}
