//import class;
import java.awt.*;
import java.awt.event.*;

import static java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY;


public class DownloadTool extends Frame
{
    private static DownloadTool dt = null;

    private DownloadTool() {}

    /**
     * 单例设计模式，返回一个对象的引用
     * */
    public static DownloadTool getInstance()
    {
        dt = new DownloadTool();
        return dt;
    }

    public static void main(String[] args)
    {
        DownloadTool.getInstance().launch();
    }

    private TextArea urlText = new TextArea("Please input the url here !", 10, 10, SCROLLBARS_VERTICAL_ONLY);
    private TextArea fileText = new TextArea("Z:/singer - song.mp3", 10, 10, SCROLLBARS_VERTICAL_ONLY);

    // 获取用户的屏幕分辨率
    private final int userScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int userScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    // 程序界面位置和大小的常量
    private final int windowLocationX = userScreenWidth / 3;
    private final int windowLocationY = userScreenHeight / 4;
    private final int windowSizeW = userScreenWidth / 3;
    private final int windowSizeH = userScreenHeight / 2;

    // 文本框位置和大小的常量
    private final int textAreaSizeW = windowSizeW - 10;
    private final int textAreaSizeH = windowSizeH / 3;
    private final int urlAreaLocationX = 5;
    private final int urlAreaLocationY = 28;
    private final int fileAreaLocationX = urlAreaLocationX;
    private final int fileAreaLocationY = windowSizeH / 3 + 28;

    // 开始按钮位置和大小的常量
    private final int buttonLocationX = windowSizeW / 100 * 80;
    private final int buttonLocationY = fileAreaLocationY + textAreaSizeH;
    private final int buttonSizeW = windowSizeW - buttonLocationX - 5;
    private final int buttonSizeH = windowSizeH - buttonLocationY - 5;

    // 状态标签位置和大小的常量
    private final int stateLocationX = (buttonLocationX - fileAreaLocationX) / 10 * 2;
    private final int stateLocationY = buttonLocationY;
    private final int stateSizeW = (buttonLocationX - stateLocationX);
    private final int stateSizeH = windowSizeH - stateLocationY;

    private void launch()
    {
        // 设置窗口的属性
        setTitle("文件下载器 v1.0");
        setLocation(windowLocationX, windowLocationY);
        setSize(windowSizeW, windowSizeH);

        setLayout(null);
        setVisible(true);
        setResizable(false);

        // 设置文本栏的属性
        urlText.setLocation(urlAreaLocationX, urlAreaLocationY);
        urlText.setSize(textAreaSizeW, textAreaSizeH);
        urlText.setFont(new Font("Consolas", Font.PLAIN, 28));

        fileText.setLocation(fileAreaLocationX, fileAreaLocationY);
        fileText.setSize(textAreaSizeW, textAreaSizeH);
        fileText.setFont(new Font("Consolas", Font.PLAIN, 28));

        // 设置程序显示的标签
        Label state = new Label("Waiting For your operation...");
        state.setLocation(stateLocationX, stateLocationY);
        state.setSize(stateSizeW, stateSizeH);
        state.setFont(new Font("微软雅黑", Font.ITALIC, 20));
        state.setForeground(new Color(65, 141, 117));

        // 设置按钮属性
        Button begin = new Button("Start");

        begin.setLocation(buttonLocationX, buttonLocationY);
        begin.setSize(buttonSizeW, buttonSizeH);
        begin.setBackground(new Color(131, 175, 155));
        begin.setFont(new Font("Consolas", Font.PLAIN, 22));
        begin.setForeground(new Color(92, 66, 66));

        begin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                download(begin, state);
            }
        });

        // 添加一个鼠标监听器，营造一个图标动画的效果
        begin.addMouseListener(new MouseAdapter()
        {
            int fontSize = begin.getFont().getSize();
            public void mouseEntered(MouseEvent e)
            {
                for (int i = 0; i < 30; i++)
                {
                    begin.setFont(new Font("Consolas", Font.PLAIN, ++fontSize));
                    try
                    {
                        Thread.sleep(2);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }

            public void mouseExited(MouseEvent e)
            {
                for (int i = 0; i < 30; i++)
                {
                    begin.setFont(new Font("Consolas", Font.PLAIN, --fontSize));
                    try
                    {
                        Thread.sleep(2);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // 将资源添加到窗口
        add(urlText);
        add(fileText);
        add(begin);
        add(state);

        // 响应关闭按钮
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // 响应鼠标按键
        urlText.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                if (urlText.getText().contains("Please input the url here !"))
                {
                    urlText.setText("");
                }
            }

            public void mouseExited(MouseEvent e)
            {
                if (urlText.getText().equals(""))
                {
                    urlText.setText("Please input the url here !");
                }
            }
        });
    }

    private void download(Button begin, Label state)
    {
        String url = urlText.getText();
        String file = fileText.getText();

        Font f = begin.getFont();
        begin.setFont(new Font("Consolas", Font.PLAIN, 22));
        begin.setLabel("downloading...");

        try
        {
            state.setForeground(new Color(65, 141, 117));
            state.setText("The file is downloading...");

            long time = MyFile.downloadFileFromURL(url, file);

            state.setForeground(new Color(65, 141, 117));
            state.setText("Done! Time used : " + (time / 1000.0) + " s");
        }
        catch (Exception e1)
        {
            state.setForeground(new Color(255, 0, 0));
            state.setText("URL or FileName is illegal !");
        }
        finally
        {
            begin.setLabel("Start");
            begin.setFont(f);
        }
    }
}
