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
	
}
