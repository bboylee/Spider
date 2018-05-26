package bboylee.origin;


import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderUtil {
    private  static final String REGEX = "\"thumbURL\":\"(https://[^\"]+)\"";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private  static final String URL = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%s&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%s&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=%d&rn=%d";
    //格式化
    public static String urlFormat(String queryParam,int pagenumber) throws UnsupportedEncodingException {

        String str = null;

        String s = URLEncoder.encode(queryParam, "UTF-8");
        str = String.format(URL,s,s,pagenumber*30,pagenumber*30);

        return str;
    }
//把json文件解析到内存中
    public static StringBuffer jsonParser(String filePath)  {
        StringBuffer sb = new StringBuffer();
       String  data = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((data=br.readLine() )!= null) {
                sb.append(data);
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }finally
        {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb;

    }
//提取文件中的URL
    public static int urlFinder(ConcurrentLinkedQueue<String> queue,StringBuffer buffer){
        int count = 0;
        Matcher matcher = PATTERN.matcher(buffer);

        while (matcher.find()){
            queue.offer(matcher.group(1));
            count++;
            }


        return count;
    }

}
