/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.data;

import java.util.ArrayList;

/**
 * The Class Sample.
 */
public final class Sample {

	/** The documents. */
	private ArrayList<Document> documents = new ArrayList<Document>();
	
	/** The qid. */
	private int qid;
	
	/**
	 * Instantiates a new sample.
	 *
	 * @param qid query id
	 */
	public Sample(int qid)
	{
		this.qid = qid;
	}
	
	/**
	 * Adds the.
	 *
	 * @param doc the doc
	 */
	public void add(Document doc)
	{
		documents.add(doc);
	}

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public ArrayList<Document> getDocuments() {
		return documents;
	}

	/**
	 * Gets the qid.
	 *
	 * @return the qid
	 */
	public int getQid() {
		return qid;
	}
	
}
