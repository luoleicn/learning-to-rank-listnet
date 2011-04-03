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
import java.io.PrintWriter;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collections;

import org.omg.Dynamic.Parameter;

import listnet.data.Document;
import listnet.data.MSDocument;
import listnet.module.ListNetModule;
import listnet.module.Module;
import listnet.parameter.Parameters;

/**
 * 用于测试.
 */
public class Main {

	private static class IndexScore implements Comparable<IndexScore>
	{
		private int index;
		private double score;
		
		public IndexScore(int index, double score)
		{
			this.index = index;
			this.score = score;
		}

		@Override
		public int compareTo(IndexScore o) {
			if (this.score > o.score)
				return -1;
			else if (this.score < o.score)
				return 1;
			else 
				return 0;
		}
	
		
	}
	
	public static void main(String[] args) throws IOException, IllegalClassFormatException
	{
		/*
		 * 训练以及持久化
		 */
//		String input = "train.txt";
//		String output = "msdata.listnet.module";
//		int round = 4000;
//		for (int i=0; i<args.length; )
//		{
//			if (args[i].equals("-i"))
//			{
//				input = args[i+1];
//				i+=2;
//			}
//			else if (args[i].equals("-o"))
//			{
//				output = args[i+1];
//				i+=2;
//			}
//			else if (args[i].equals("-t")) 
//			{
//				round = Integer.parseInt(args[i+1]);
//				i+=2;
//			}
//		}
//		Parameters.setEpochnum(round);
//		Module m = Trainer.train(new File(input));
//		m.write(new File(output));
		
		/*
		 * 从文件中读出模型，并用于测试
		 */
		String module = "msdata.listnet.module";
		String input = "test.txt";
		String output = "myRank.txt";
		for (int i=0; i<args.length; )
		{
			if (args[i].equals("-i"))
			{
				input = args[i+1];
				i+=2;
			}
			else if (args[i].equals("-o"))
			{
				output = args[i+1];
				i+=2;
			}
			else if (args[i].equals("-m"))
			{
				module = args[i+1];
				i+=2;			             
			}
		}
		Module m = ListNetModule.getInstance(new File(module));
		ModuleTest tester = new ModuleTest(m);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		
		PrintWriter writer = new PrintWriter(new File(output));
		String line = null;
		ArrayList<IndexScore> list = new ArrayList<Main.IndexScore>();
		int lastqid = -1;
		StringBuffer sb = new StringBuffer();
		int index=0;
		while ((line = reader.readLine()) != null)
		{
			Document doc = MSDocument.parseDoc(line);
			doc = m.getNormalizer().normalize(doc);
			
			if (doc.getQid() != lastqid)
			{
				lastqid = doc.getQid();
				if (list !=null && list.size() != 0)
				{
					Collections.sort(list);
					int[] sorted = new int[list.size()];
					for (int i=0; i<sorted.length; i++)
					{
						sorted[list.get(i).index] = i+1;
					}
					for (int i=0; i<sorted.length; i++)
					{
						if (i != sorted.length-1)
							writer.write(sorted[i] + " ");
						else
							writer.write(sorted[i]+"");
					}
					writer.write("\n");
					list = new ArrayList<Main.IndexScore>();
					index = 0;
				}
			}
			list.add(new IndexScore(index++, tester.test(doc.getFeatures())));

		}	
		
		Collections.sort(list);
		int[] sorted = new int[list.size()];
		for (int i=0; i<sorted.length; i++)
		{
			sorted[list.get(i).index] = i+1;
		}
		for (int i=0; i<sorted.length; i++)
		{
			if (i != sorted.length-1)
				writer.write(sorted[i] + " ");
			else
				writer.write(sorted[i]+"");
		}
		writer.write("\n");
		writer.close();
	}
}
