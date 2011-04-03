/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import listnet.data.Document;
import listnet.data.MSDocument;
import listnet.data.Sample;


/**
 * The Class MSDataReaderMem.和MSDataRader的区别在于把所有数据都保存在内存中
 */
public class MSDataReaderMem implements DataReader{

	/** 保存文件句柄，主要目的是为了在reset的时候可以重新在这个文件上打开读入流. */
	private File file;
	
	/** The reader. */
	private BufferedReader reader;
	
	/** The sample. */
	private Sample sample;
	
	/** The samples. */
	private ArrayList<Sample> samples;
	
	/** The cur. */
	private int cur = 0;
	
	/**
	 * 被构造函数调用，读入文档中第一个doc，初始化sample，把doc加入sample.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void init() throws IOException
	{
		String line = reader.readLine();
		
		if (line == null)
			throw new NullPointerException("文档为空");
		
		Document startDoc = MSDocument.parseDoc(line);
		this.sample = new Sample(startDoc.getQid());
		this.sample.add(startDoc);
	}
	
	/**
	 * Instantiates a new MsDataReader.
	 *
	 * @param in the in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public MSDataReaderMem(File in) throws IOException
	{
		if (in == null)
			throw new NullPointerException("传入MSDataReader的输入流为null");
		
		this.file = in;
		this.reader = new BufferedReader(new FileReader(in));
		this.samples = new ArrayList<Sample>();
		init();
		
		Sample s = null;
		while ((s = readOneSample()) != null)
		{
			this.samples.add(s);
		}
		
		reader.close();
		cur = 0;
		
	}
	
	/* (non-Javadoc)
	 * @see listnet.process.DataReader#getNextSample()
	 */
	@Override
	public Sample getNextSample() {		
		if (cur >= this.samples.size())
			return null;
		
		Sample s = this.samples.get(cur);
		cur++;
		return s;
	}

	/**
	 * Read one sample.
	 *
	 * @return the sample
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Sample readOneSample() throws IOException
	{
		Sample res = null;
		String line = null;
		
		while ((line = reader.readLine()) != null)
		{
			Document doc = MSDocument.parseDoc(line);
			if (doc.getQid() != this.sample.getQid())
			{
				res = this.sample;
				this.sample = new Sample(doc.getQid());
				this.sample.add(doc);
				break;
			}
			else
			{
				this.sample.add(doc);
			}
		}
		
		if (line == null)
		{
			res = this.sample;
			this.sample = null;
		}
			
		
		return res;
	}
	/* (non-Javadoc)
	 * @see listnet.process.DataReader#reset()
	 */
	@Override
	public void reset() throws IOException {
		cur = 0;
	}

	/* (non-Javadoc)
	 * @see listnet.process.DataReader#close()
	 */
	@Override
	public void close() {}
		

}
