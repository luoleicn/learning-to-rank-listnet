/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.data;

import java.util.ArrayList;

/**
 * 文档类接口.
 */
public interface Document {

	
	/**
	 * Gets the features.
	 * 注意：实现者在这个例子方法的实现过程中，不必保证正规化，使用者必须自行正规化
	 *
	 * @return the features
	 */
	public abstract ArrayList<Double> getFeatures();
	
	/**
	 * Gets the relevance.
	 *
	 * @return the relevance
	 */
	public abstract int getRelevance();
	
	/**
	 * Gets the query id.
	 *
	 * @return the query id
	 */
	public abstract int getQid();
}
