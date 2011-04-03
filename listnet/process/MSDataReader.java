/*
 * 作者：罗磊
 * 任何问题可以联系作者的Email：luoleicn@gmail.com
 * 
 * 遵循知识共享（CC By2.5）协议详见http://creativecommons.org/licenses/by/2.5/cn/
 */
package listnet.process;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.print.Doc;

import listnet.data.Document;
import listnet.data.MSDocument;
import listnet.data.Sample;

// TODO: Auto-generated Javadoc
/**
 * The Class MSDataReader.
 */
public class MSDataReader implements DataReader{

	/** 保存文件句柄，主要目的是为了在reset的时候可以重新在这个文件上打开读入流. */
	private File file;
	
	/** The reader. */
	protected BufferedReader reader;
	
	/** The sample. */
	protected Sample sample;
	
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
	public MSDataReader(File in) throws IOException
	{
		if (in == null)
			throw new NullPointerException("传入MSDataReader的输入流为null");
		
		this.file = in;
		this.reader = new BufferedReader(new FileReader(in));
		
		init();
	}
	
	/* (non-Javadoc)
	 * @see listnet.process.DataReader#getNextSample()
	 */
	@Override
	public Sample getNextSample() throws IOException {
		
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
		this.reader.close();
		this.reader = new BufferedReader(new FileReader(this.file));
		init();
	}

	/* (non-Javadoc)
	 * @see listnet.process.DataReader#close()
	 */
	@Override
	public void close() {
		
		try {
			this.reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	
	/**
	 * 测试.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException
	{
		MSDataReader reader = new MSDataReader(new File("/home/luolei/LearningToRank/msdata/Fold1/vali.txt"));
		Sample sample = reader.getNextSample();
//		for (int i=0; i<136; i++)
//		{
//			System.out.println("f[" + i + "] = " + sample.getDocuments().get(0).getFeatures().get(i));
//		}	  
		System.out.println("size = " + sample.getDocuments().size());
			
		sample = reader.getNextSample();
		for (int i=0; i<136; i++)
		{
			System.out.println("f[" + i + "] = " + sample.getDocuments().get(0).getFeatures().get(i));
		}	  
		System.out.println("size = " + sample.getDocuments().size());
	}
}
