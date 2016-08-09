package com.springapp.mvc.Util;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/13.
 */
public class WriteReadFiles {
    private static Logger log = LoggerFactory.getLogger(WriteReadFiles.class);
    public boolean writeFile(String content) {
        String filename = getClass().getResource("/").getPath().toString() + "accounts.txt";
        log.debug("In WriteReadFiles, {}", filename);
        try {
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f, true));
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.flush();
            writer.newLine();
            writer.flush();
            write.close();
            writer.close();
            log.debug("Write File complete!");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<String> readFile(){
        String filename = getClass().getResource("/").getPath().toString() + "accounts.txt";
        log.debug("In WriteReadFiles, {}", filename);
        //String content = "";
        ArrayList<String> arr = new ArrayList<String>();
        try {
            File f = new File(filename);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(filename));
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null){
                    //content += line;
                    arr.add(line);
                }
                //content = content.substring(0, content.length() - 1);
                read.close();
                reader.close();
                log.debug("Read File complete!");
                //return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return new String();
        return arr;
    }
}
