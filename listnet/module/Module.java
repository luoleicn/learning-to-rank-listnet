/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import listnet.process.Normalizer;

/**
 * The Interface Module.
 */
public interface Module {


	
	/**
	 * 把模型写入文件.
	 *
	 * @param f 模型文件
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public abstract void write(File f) throws FileNotFoundException, IOException;
	
	/**
	 * Gets the weights.
	 *
	 * @return the weights
	 */
	public abstract double[] getWeights();
	
	/**
	 * Gets the normalizer.
	 *
	 * @return the normalizer
	 */
	public abstract Normalizer getNormalizer();
}
