package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileUtils {
    public static boolean deleteDirectory(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.error("删除目录失败:{}目录不存在！", dir);
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.error("删除目录失败");
            return false;
        }

        //删除当前目录
        if (dirFile.delete()) {
            log.error("删除目录{}成功", dir);
            return true;
        } else {
            log.error("删除目录{}成功", dir);
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            Boolean succeedDelete = file.delete();
            if (succeedDelete) {
                log.info("删除单个文件{}成功", fileName);
                return true;
            } else {
                log.error("删除单个文件{}失败!", fileName);
                return true;
            }
        } else {
            log.error("删除单个文件{}失败!", fileName);
            return false;
        }
    }
}
