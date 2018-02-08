import java.io.*;
import java.net.*;



public final class MyFile
{
    /**
     * 功能：把目录分层打印出来
     * @param directory 要被打印的目录
     * */
    public static void printDirectory(File directory)
    {
        System.out.println(directory);
        int spaceNum = 1;

        if (directory.isDirectory())
        {
            File[] fs = directory.listFiles();

            if (fs == null)
            {
                return;
            }

            for (File file : fs)
            {
                printSpace(spaceNum++);
                printDirectory(file);
            }
        }

    }

    // 打印空格
    private static void printSpace(int spaceNum)
    {
        for (int i = 0; i < spaceNum; i++)
        {
            System.out.print("   ");
        }
    }

    /**
     * 功能：文件拷贝
     * @param srcFile 要被拷贝的文件
     * @param desFile 拷贝之后的文件名
     * */
    public static void copyFile(File srcFile, File desFile) throws IOException
    {
        long begin = System.currentTimeMillis();

        InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(desFile));

        byte[] buffer = new byte[1024 * 8];

        int len = 0;
        while ((len = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();

        long end = System.currentTimeMillis();

        System.out.println("拷贝花费时间为 " + (end - begin) + " ms");
    }

    /**
     * 功能：下载文件
     * @param fileUrl 文件的链接
     * @param saveFilePath 保存的文件名，包括位置，如 Z:/demo.mp3
     */
    public static long downloadFileFromURL(String fileUrl, String saveFilePath) throws Exception
    {
        long begin = System.currentTimeMillis();

        URL url = new URL(fileUrl);

        URLConnection con = url.openConnection();

        InputStream in = new BufferedInputStream(con.getInputStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(saveFilePath));

        byte[] buffer = new byte[1024 * 8];

        int len = 0;
        while ((len = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();

        long end = System.currentTimeMillis();

        System.out.println("下载花费时间为 " + (end - begin) + " ms");

        return (end - begin);
    }

}
