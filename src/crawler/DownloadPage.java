package crawler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DownloadPage {
	public  String getFileNameByUrl(String url,String contentType)
	{
		url=url.substring(7);//remove http://
		if(contentType.indexOf("html")!=-1)//text/html
		{
			url= url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
			return url;
		}
		else//如application/pdf
		{
			return url.replaceAll("[\\?/:*|<>\"]", "_")+"."+ 
          contentType.substring(contentType.lastIndexOf("/")+1);
		}	
	}

	/*
	 */
	private void saveToLocal(byte[] data,String filePath)
	{
		try {
			DataOutputStream out=new DataOutputStream(
					new FileOutputStream(new File(filePath)));
			for(int i=0;i<data.length;i++)
			out.write(data[i]);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public  void test(String path)throws Exception{
		String filePath=null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(path);
		CloseableHttpResponse response = httpclient.execute(httpget);
		
        HttpEntity entity = response.getEntity();
		try {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
    			filePath="/home/ubuntu/test/temp/"+getFileNameByUrl(path,entity.getContentType().toString());
            } else {
            	filePath = null;
                //throw new ClientProtocolException("Unexpected response status: " + status);
            }
			byte[] responseBody = EntityUtils.toByteArray(entity);//读取为字节数
			saveToLocal(responseBody,filePath);
            System.out.println(entity.getContentType());
			System.out.println(getFileNameByUrl(path,entity.getContentType().toString()));
			//System.out.println(getFileNameByUrl(path,response.getHeaders("Content-Type")));
		} finally {
		    response.close();
		}
	}
	public static void main(String[] args) {
		DownloadPage mytest=new DownloadPage();
		String path="http://www.google.com/";
		try{
			mytest.test(path);
		}catch (Exception e){
			System.out.println("some thing wrong with the test function");
		}
	}

}
