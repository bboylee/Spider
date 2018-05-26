import bboylee.origin.MyDownloader;
import bboylee.origin.MySpider;
import bboylee.origin.SpiderUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
    @org.junit.Test
    public void test01() throws UnsupportedEncodingException {
        String url = SpiderUtil.urlFormat("海贼王",2);
        System.out.println(url);

    }
    @org.junit.Test
    public void test02() throws UnsupportedEncodingException {
        String url = SpiderUtil.urlFormat("海贼王",600);

        MyDownloader download = new MyDownloader();
        download.setJsonPath("e:\\");
        download.downloadPage(url);
    }
@org.junit.Test
    public void test03() throws IOException {
    StringBuffer buffer = SpiderUtil.jsonParser("e:\\1.json");
    System.out.println(buffer);
}
@org.junit.Test
    public void test04() throws IOException {
    StringBuffer buffer = SpiderUtil.jsonParser("e:\\1.json");
    ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<String>();
    int count = SpiderUtil.urlFinder(urlQueue,buffer);
    MyDownloader myDownloader = new MyDownloader();
    myDownloader.setImagePath("e:\\image");
    myDownloader.downloadImage(urlQueue);


}
@org.junit.Test
    public void test05(){
    MySpider spider = new MySpider();
   for(int i= 1 ;i<=10;i++){
       spider.start("美女",i,"e:\\","e:\\image");
   }
    }
}
