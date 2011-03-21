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

import listnet.data.Document;
import listnet.data.MSDocument;
import listnet.module.ListNetModule;
import listnet.module.Module;

/**
 * 用于测试.
 */
public class Main {

	public static void main(String[] args) throws IOException, IllegalClassFormatException
	{
		/*
		 * 训练以及持久化
		 */
		Module m = Trainer.train(new File(args[0]));
		m.write(new File("msdata.listnet.module"));
		
		/*
		 * 从文件中读出模型，并用于测试
		 */
		m = ListNetModule.getInstance(new File("msdata.listnet.module"));
		ModuleTest tester = new ModuleTest(m);
		BufferedReader reader = new BufferedReader(new FileReader(new File("/home/luolei/LearningToRank/msdata/Fold1/vali.txt")));
		Document doc = MSDocument.parseDoc(reader.readLine());
		doc = m.getNormalizer().normalize(doc);
		System.out.println("res = " + tester.test(doc.getFeatures()) + " ans = " + doc.getRelevance());
	}
}
