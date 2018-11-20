package com.zdkzdk.utils;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class ToDialog implements ActionListener {

    //创建组件的对象。
    private JFrame frame = new JFrame("WordToHtml-zdkzdk");// 框架布局
    private JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
    private Container con = new Container();//
    private JLabel label1 = new JLabel("选择word文件");
    private JLabel label2 = new JLabel("选择生成的html文件的位置（默认桌面）");
    private JTextField text1 = new JTextField("默认d盘");// TextField 目录的路径
    private JTextField text2 = new JTextField("C:\\Users\\Administrator\\Desktop");// 文件的路径
    private JButton button1 = new JButton("...");// 选择
    private JButton button2 = new JButton("...");// 选择
    JFileChooser jfc = new JFileChooser();// 文件选择器

    JButton button3 = new JButton("Transform");//
    JButton button4 = new JButton("<html>用word<br>打开文件</html>");
    JButton button5 = new JButton("<html>用绑定程序<br>打开生成的html</html>");

    JProgressBar progressBar = new JProgressBar();//进度条
    int count = 0;
    JTextField textField = new JTextField(10);

    //转换的源文件和目的地
    String filename = "", savefilename = "C:\\Users\\Administrator\\Desktop";

    //构造方法，用来构建图形化程序，初始化组件和frame
    ToDialog() throws Exception {

        jfc.setCurrentDirectory(new File("d://")); // 文件选择器的初始目录定为d盘


        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(500, 500);// 设定窗口大小
        frame.setContentPane(tabPane);// 设置布局

        label1.setBounds(10, 10, 400, 30);
        text1.setBounds(10, 35, 300, 20);
        button1.setBounds(311, 35, 50, 20);

        label2.setBounds(10, 60, 400, 30);
        text2.setBounds(10, 85, 300, 20);
        button2.setBounds(311, 85, 50, 20);

        button3.setBounds((int) frame.getSize().getWidth() / 2 - 50, 110, 100, 100);
        button4.setBounds(100, 110, 100, 100);
        button5.setBounds(300, 110, 100, 100);

        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        button4.addActionListener(this); // 添加事件处理
        button5.addActionListener(this); // 添加事件处理

//        progressBar.setBounds(11,11,100,100);
//        progressBar.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                System.out.println("当前进度值: " + progressBar.getValue() + "; " +
//                        "进度百分比: " + progressBar.getPercentComplete() +"@" + e.getSource().toString());
//            }
//        });
        //button3.setVisible(false);//默认隐藏。
        //add()是用来在主面板上添加元素的
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(label2);
        con.add(text2);
        con.add(button2);
        con.add(button3);
        con.add(button4);
        con.add(button5);


        con.add(progressBar);
        con.add(textField);


        tabPane.add("面板1", con);// 添加布局1
        frame.setVisible(true);// 窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序

    }

    /**
     * 事件监听的方法
     */
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();//getSource()返回触发事件的对象。
        try {

            if (button == button1) {// 判断触发事件的按钮是哪个
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择文件 ,应该是先设置，再打开。
                jfc.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().endsWith(".docx");
                    }

                    @Override
                    public String getDescription() {
                        return "版本》=word07(docx)";
                    }
                });
                jfc.showOpenDialog(null);// 此句是打开文件选择器界面
                File f = jfc.getSelectedFile();
                if (f != null) {
                    filename = jfc.getSelectedFile().getAbsolutePath();
                    text1.setText(filename);
                }
            } else if (button.equals(button2)) {
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设定只能选择文件 ,应该是先设置，再打开。
                jfc.showOpenDialog(null);// 此句是打开文件选择器界面
                savefilename = jfc.getSelectedFile().getAbsolutePath();// f为选择到的文件
                text2.setText(savefilename);
            } else if (button.equals(button3)) {

                if (!Utils.isBlank(filename) && !Utils.isBlank(savefilename)) {
                    Utils.wordToHtml(filename, savefilename);
                    //创建工作线程

                    // progressBar.setSize(400, 400);
//                    progressBar.setVisible(true);
//
//                     new WorkThead().start();


                } else {
                    JOptionPane.showMessageDialog(null, "请选择路径", "提示", 2);
                }
            } else if (button.equals(button4)) {
                if (!Utils.isBlank(filename)) {
                    Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler " + filename);
                } else {
                    JOptionPane.showMessageDialog(null, "请选择路径", "提示", 2);
                }

            } else if (button.equals(button5)) {
                String name = new File(filename).getName();
                String htmPath = savefilename + "\\" + name.replace(".docx", ".htm");
                if (htmPath.endsWith("\\")) {
                    JOptionPane.showMessageDialog(null, "html还未生成，打开的是目录", "提示", 2);
                }
                Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler " + htmPath);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

//    class WorkThead extends Thread {
//        public void run() {
//            Utils.wordToHtml(filename, savefilename);
//            while (count < 5) {
//
//                count++;
//
//                        progressBar.setValue(count);
//                        textField.setText(count + "%");
//
//            }
//        }
//
//    }

    public static void main(String[] args) throws Exception {
        new ToDialog();
    }
}