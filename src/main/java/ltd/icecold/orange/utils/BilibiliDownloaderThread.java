package ltd.icecold.orange.utils;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 多线程下载
 *
 * @author ice-cold
 */
public class BilibiliDownloaderThread {

    private final String path;

    private final File targetFile;

    private final int threadNum;

    private DownThread[] threads;

    private int fileSize;

    private String referer;

    public BilibiliDownloaderThread(String url,File targetFile, int threadNum, String referer)
    {
        this.path = url;
        this.threadNum = threadNum;
        this.threads = new DownThread[threadNum];
        this.targetFile = targetFile;
        this.referer = referer;
    }

    public void start() throws Exception
    {
        URL url=new URL(path);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        conn.setRequestProperty("Referer", referer);
        conn.setRequestProperty("Range","bytes=0-");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        conn.setRequestProperty("Origin", "https://www.bilibili.com");
        conn.setRequestProperty("Connection","Keep-Alive");

        fileSize=conn.getContentLength();
        conn.disconnect();
        int currentPartSize=fileSize/threadNum+1;
        RandomAccessFile file=new RandomAccessFile(targetFile,"rw");
        file.setLength(fileSize);
        file.close();
        for(int i=0;i<threadNum;i++)
        {
            int startPos=i*currentPartSize;
            RandomAccessFile currentPart=new RandomAccessFile(targetFile,"rw");
            currentPart.seek(startPos);
            threads[i]=new DownThread(startPos,currentPartSize,currentPart);
            threads[i].start();
        }
    }

    public double getCompleteRate()
    {
        int sumSize=0;
        for(int i=0;i<threadNum;i++)
        {
            sumSize+=threads[i].length;
        }
        return sumSize*1.0/fileSize;
    }

    public class DownThread extends Thread
    {
        private int startPos;
        private int currentPartSize;
        private RandomAccessFile currentPart;
        public int length;

        public DownThread(int startPos,int currentPartSize,RandomAccessFile currentPart)
        {
            this.startPos=startPos;
            this.currentPartSize=currentPartSize;
            this.currentPart=currentPart;
        }

        @Override
        public void run()
        {
            try
            {
                URL url=new URL(path);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "*/*");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
                conn.setRequestProperty("Referer", referer);
                conn.setRequestProperty("Range","bytes=0-");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
                conn.setRequestProperty("Origin", "https://www.bilibili.com");
                conn.setRequestProperty("Connection","Keep-Alive");
                InputStream inStream=conn.getInputStream();

                inStream.skip(this.startPos);
                byte[] buffer=new byte[1024];
                int hasread=0;

                while(length<currentPartSize&&(hasread=inStream.read(buffer))!=-1)
                {
                    currentPart.write(buffer,0,hasread);
                    length+=hasread;
                }
                currentPart.close();
                inStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}
