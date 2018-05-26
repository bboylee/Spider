package bboylee.origin;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class MySpider {
    private MyDownloader myDownloader= new MyDownloader();

//入口函数 参数依次为 爬取的图片类型 页数 存储json的路径 储存图片的路径
//需要循环批量爬取的话将start函数置于循环中 把循环变量当作页数传入 模拟翻页
        public void start(String query,int number,String jsonPath,String imagePath) {
            try {

            String urlFormat = SpiderUtil.urlFormat(query, number);

            setJsonPath(jsonPath);

            setImagepath(imagePath);

        myDownloader.downloadWithUtil(urlFormat);

        StringBuffer buffer = SpiderUtil.jsonParser(jsonPath + "/1.json");
        ConcurrentLinkedQueue<String > queue = new ConcurrentLinkedQueue<String>();
        SpiderUtil.urlFinder(queue,buffer);
        myDownloader.downloadImage(queue);



    } catch (IOException e) {
        e.printStackTrace();
    }

}
    public void setJsonPath(String path){
    myDownloader.setJsonPath(path);
}
    public void setImagepath(String path){
        myDownloader.setImagePath(path);
    }
}
