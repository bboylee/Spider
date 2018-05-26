package bboylee.origin;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.*;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyDownloader {
    public static int downloadcount= 0;

    private  String jsonPath;
    private  String imagePath;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
         File file = new File(imagePath);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }
//自己手写的下载方法效率不高
    public void downloadPage(String url){
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
            connection.setRequestProperty("Referer","https://image.baidu.com/search/index");
            connection.setAllowUserInteraction(false);
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            BufferedWriter bw = new BufferedWriter(new FileWriter(jsonPath+"/1.json",true));
            String data = null;
            while((data=br.readLine()) != null){
                bw.write(data);
                bw.flush();
            }
            if(br != null){
                br.close();
            }
            if(bw != null){
                bw.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//使用了HTTP工具的下载方法
    public void downloadWithUtil( String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);

        try{
            HttpEntity entity = response.getEntity();
            entity.writeTo(new FileOutputStream(jsonPath+"1.json"));

        }finally{
            client.close();
        }
    }
//下载图片的方法  参数是URL队列
public int downloadImage(ConcurrentLinkedQueue<String> queue){
      int count  = 0;

      ArrayList<String> downloadedList = new ArrayList<String>();
      for(count= 0 ;count <queue.size();count++){
                String url = queue.poll();
          if(!downloadedList.contains(url)){
             CloseableHttpClient client = HttpClients.createDefault();
             HttpGet get  = new HttpGet(url);
              try {
                  String imageName = url.substring(url.lastIndexOf("/")+1);  //把图片链接的子串用作文件名
                  CloseableHttpResponse response =client.execute(get);
                  HttpEntity entity = response.getEntity();
                  entity.writeTo(new FileOutputStream(imagePath+"/"+imageName));
                 System.out.println("正在下载第"+downloadcount+++"张图片");
                  downloadedList.add(url);
              } catch (IOException e) {
                  e.printStackTrace();
              }finally{
                  try {
                      client.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

          }else{
              continue;
          }

      }

return count;

}

}
