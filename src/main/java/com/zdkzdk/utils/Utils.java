package com.zdkzdk.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    /**
     * 判断一个字符串是否为null且为""
     *
     * @param string
     * @return 如为null或者为""返回true
     */
    public static boolean isBlank(String string) {

        return string != null && !"".equals(string) ? false : true;
    }

    /**
     * 将指定路径下docx转成html并保存在directory
     */
    public static void wordToHtml(String filePath, String saveDirectory) {
        //根据电脑操作系统位数将jacob的dll文件复制到jdk/bin
        Properties p_ps = System.getProperties();
        String OperatingSystemNumber = p_ps.getProperty("sun.arch.data.model");
        byte num = 0;
        if (OperatingSystemNumber.contains("64")) {
            num = 64;
        } else if (OperatingSystemNumber.contains("32")) {
            num = 86;
        }

        File file = new File(System.getProperty("java.home") + "\\bin\\jacob-1.19-x" + num + ".dll");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileUtils.copyFile(new File("dll\\jacob-1.19-x" + num + ".dll"), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //利用jaboc转换
        ActiveXComponent app = new ActiveXComponent("WORD.Application");// 启动word进程
        app.setProperty("Visible", new Variant(false));//设置用后台隐藏方式打开，即不在窗口显示word程序
        Dispatch documents = app.getProperty("Documents").toDispatch();//获取操作word的document调用
        Dispatch doc = Dispatch.call(documents, "Open", filePath).toDispatch();//调用打开命令，同时传入word路径
        String name = new File(filePath).getName().replace(".docx",".htm");//调用另存为命令，同时传入html的路径
        Dispatch.invoke(doc, "SaveAs", Dispatch.Method,
                new Object[]{saveDirectory + "\\" + name, new Variant(8)}, new int[1]);
        Dispatch.call(doc, "Close", new Variant(0));//关闭document对象
        Dispatch.call(app, "Quit");//关闭WINWORD.exe进程
        //清空对象
        doc = null;
        app = null;
    }


}
