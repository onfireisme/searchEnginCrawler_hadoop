package searchEnginCrawler_hadoop;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
	public static void main(String[] args) throws Exception {
		ArrayList urlList=new ArrayList(200);
		String path="/home/ubuntu/test/url.txt";
		ReadFromFile.readFileByLine(path,urlList);
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        //System.out.println(urlList);

        for(int i=0;i<urlList.size();i++){
        	threadPool.execute(new CrawlerThread(urlList.get(i).toString()));
        }
        threadPool.shutdown();
		/*
		DownloadPage mytest=new DownloadPage();
		String path="http://www.google.com/";
		String path2="http://www.baidu.com/";
		new CrawlerThread(path);
		new CrawlerThread(path2);
		*/
	}
}
