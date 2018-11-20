package com.zdkzdk;



/**
 * swing实现进度条的不断刷新
 * 1、执行进度条显示时，耗时的等待操作在新建的工作线程里面完成；
 * 2、进度条和数据的更新，在事件派发线程EDT中进行
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JProcessBarTest extends JFrame{

    private static final long serialVersionUID = 1L;
    private static final String STRING = "Completed:";

    private JProgressBar progressBar = new JProgressBar();
    private JTextField textField = new JTextField(10);
    private JButton start = new JButton("start");
    private JButton end = new JButton("end");

    private boolean state = false;
    private int count = 0;

    //工作线程workThead
    private Thread workThead = null;
    private Runnable run = null;

    public JProcessBarTest() {

        this.setLayout(new FlowLayout());
        this.add(progressBar);
        textField.setEditable(false);
        this.add(textField);
        this.add(start);
        this.add(end);

        //开始按钮的监听器，负责由事件触发后创建工作线程
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                state = true;
                if (workThead == null) {
                    workThead = new WorkThead();
                    workThead.start();
                }
            }
        });

        end.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                state = false;
            }
        });

    }

    class WorkThead extends Thread{

        public void run() {

            while (count < 100) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (state) {
                    count++;
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //更新操作通过事件派发线程完成（一般实现Runnable()接口）
                            progressBar.setValue(count);
                            textField.setText(STRING + String.valueOf(count) + "%");

                        }
                    });
                }
            }
        }

    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        JProcessBarTest jTest = new JProcessBarTest();
        jTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jTest.setSize(400, 400);
        jTest.setVisible(true);

    }

}
