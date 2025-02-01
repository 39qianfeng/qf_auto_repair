package com.qianfeng.task;

import org.python.util.PythonInterpreter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: qianfeng
 * @Date: 2024/11/20/23:33
 * @Description:
 */
@Component
public class B {
    @Scheduled(cron = "0 0 * * * ? ") //每小时触发一次
    public void getTop100(){
//        System.setProperty("python.import.site", "false");
//        PythonInterpreter interpreter = new PythonInterpreter();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("");
//        System.out.println(resourcesPath);
//        interpreter.execfile("src/main/resources/pythonCode/main.py");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("src/main/resources/pythonCode/main.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
