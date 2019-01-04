package demo.spring.boot.demospringboot.util;

import demo.spring.boot.demospringboot.parse.mysql.parse.vo.AssociationJavaTable;
import demo.spring.boot.demospringboot.parse.mysql.parse.vo.JavaTable;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int BUFFER_SIZE = 2 * 1024;

    private static final String tmpPath = "tmp/";

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {                           // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {          // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {                                // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));  // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    public static InputStream createFilesAndZip(JavaTable javaTable, String zipFileName, String dirPath) throws IOException {
        BufferedOutputStream voOutputStream = null;
        BufferedOutputStream daoOutputStream = null;
        BufferedOutputStream serviceOutputStream = null;
        BufferedOutputStream serviceImplOutputStream = null;
        BufferedOutputStream mapperFileOutputStream = null;

        //创建文件夹
        new File(tmpPath + javaTable.getBasePackagePath()).mkdirs();

        File voFile = new File(tmpPath + javaTable.getClassVoPath());
        voFile.createNewFile();
        voOutputStream = new BufferedOutputStream(new FileOutputStream(voFile));
        voOutputStream.write(javaTable.getClassVoStr().getBytes());
        voOutputStream.flush();

        File daoFile = new File(tmpPath + javaTable.getClassDaoPath());
        daoFile.createNewFile();
        daoOutputStream = new BufferedOutputStream(new FileOutputStream(daoFile));
        daoOutputStream.write(javaTable.getClassDaoStr().getBytes());
        daoOutputStream.flush();

        File serviceFile = new File(tmpPath + javaTable.getClassServicePath());
        serviceFile.createNewFile();
        serviceOutputStream = new BufferedOutputStream(new FileOutputStream(serviceFile));
        serviceOutputStream.write(javaTable.getClassServiceStr().getBytes());
        serviceOutputStream.flush();

        File serviceImplFile = new File(tmpPath + javaTable.getClassServiceImplPath());
        serviceFile.createNewFile();
        serviceImplOutputStream = new BufferedOutputStream(new FileOutputStream(serviceImplFile));
        serviceImplOutputStream.write(javaTable.getClassServiceImplStr().getBytes());
        serviceImplOutputStream.flush();

        File mapperFile = new File(tmpPath + javaTable.getMapperPath());
        mapperFile.createNewFile();
        mapperFileOutputStream = new BufferedOutputStream(new FileOutputStream(mapperFile));
        mapperFileOutputStream.write(javaTable.getMapperStr().getBytes());
        mapperFileOutputStream.flush();

        voOutputStream.close();
        daoOutputStream.close();
        serviceOutputStream.close();
        serviceImplOutputStream.close();
        mapperFileOutputStream.close();


        File file = new File(tmpPath + zipFileName);
        OutputStream outputStream = new FileOutputStream(file);
        ZipUtils.toZip(tmpPath + dirPath, outputStream, true);
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = new FileInputStream(tmpPath + zipFileName);


        voFile.delete();
        daoFile.delete();
        serviceFile.delete();
        serviceImplFile.delete();
        mapperFile.delete();
        FileUtils.deleteDirectory(tmpPath + dirPath);
        file.delete();

        return inputStream;
    }

    public static InputStream createFilesAndZip(List<AssociationJavaTable> associationJavaTables, String zipFileName, String dirPath) throws IOException {
        BufferedOutputStream voOutputStream = null;
        BufferedOutputStream daoOutputStream = null;
        BufferedOutputStream serviceOutputStream = null;
        BufferedOutputStream serviceImplOutputStream = null;
        BufferedOutputStream mapperFileOutputStream = null;
        BufferedOutputStream voOutputStreamAssociation = null;
        BufferedOutputStream daoOutputStreamAssociation = null;
        BufferedOutputStream serviceOutputStreamAssociation = null;
        BufferedOutputStream serviceImplOutputStreamAssociation = null;
        BufferedOutputStream mapperFileOutputStreamAssociation = null;
        File voFile = null;
        File daoFile = null;
        File serviceFile = null;
        File serviceImplFile = null;
        File mapperFile = null;
        File voFileAssociation = null;
        File daoFileAssociation = null;
        File serviceFileAssociation = null;
        File serviceImplFileAssociation = null;
        File mapperFileAssociation = null;

        for (AssociationJavaTable javaTable : associationJavaTables) {
            //创建文件夹
            new File(tmpPath + javaTable.getBasePackagePath()).mkdirs();

            if (StringUtils.isNotBlank(javaTable.getClassVoStr())) {
                voFile = new File(tmpPath + javaTable.getClassVoPath());
                voFile.createNewFile();
                voOutputStream = new BufferedOutputStream(new FileOutputStream(voFile));
                voOutputStream.write(javaTable.getClassVoStr().getBytes());
                voOutputStream.flush();
                voOutputStream.close();
            }
            if (StringUtils.isNotBlank(javaTable.getClassDaoStr())) {
                daoFile = new File(tmpPath + javaTable.getClassDaoPath());
                daoFile.createNewFile();
                daoOutputStream = new BufferedOutputStream(new FileOutputStream(daoFile));
                daoOutputStream.write(javaTable.getClassDaoStr().getBytes());
                daoOutputStream.flush();
                daoOutputStream.close();
            }

            if (StringUtils.isNotBlank(javaTable.getClassServiceStr())) {
                serviceFile = new File(tmpPath + javaTable.getClassServicePath());
                serviceFile.createNewFile();
                serviceOutputStream = new BufferedOutputStream(new FileOutputStream(serviceFile));
                serviceOutputStream.write(javaTable.getClassServiceStr().getBytes());
                serviceOutputStream.flush();
                serviceOutputStream.close();
            }
            if (StringUtils.isNotBlank(javaTable.getClassServiceImplStr())) {
                serviceImplFile = new File(tmpPath + javaTable.getClassServiceImplPath());
                serviceImplFile.createNewFile();
                serviceImplOutputStream = new BufferedOutputStream(new FileOutputStream(serviceImplFile));
                serviceImplOutputStream.write(javaTable.getClassServiceImplStr().getBytes());
                serviceImplOutputStream.flush();
                serviceImplOutputStream.close();
            }
            if (StringUtils.isNotBlank(javaTable.getMapperStr())) {
                mapperFile = new File(tmpPath + javaTable.getMapperPath());
                mapperFile.createNewFile();
                mapperFileOutputStream = new BufferedOutputStream(new FileOutputStream(mapperFile));
                mapperFileOutputStream.write(javaTable.getMapperStr().getBytes());
                mapperFileOutputStream.flush();
                mapperFileOutputStream.close();
            }
            //关联处理
            if (StringUtils.isNotBlank(javaTable.getClassAssociationVoStr())) {
                voFileAssociation = new File(tmpPath + javaTable.getClassAssociationVoPath());
                voFileAssociation.createNewFile();
                voOutputStreamAssociation = new BufferedOutputStream(new FileOutputStream(voFileAssociation));
                voOutputStreamAssociation.write(javaTable.getClassAssociationVoStr().getBytes());
                voOutputStreamAssociation.flush();
                voOutputStreamAssociation.close();
            }
            if (StringUtils.isNotBlank(javaTable.getClassAssociationDAOStr())) {
                daoFileAssociation = new File(tmpPath + javaTable.getClassAssociationDAOPath());
                daoFileAssociation.createNewFile();
                daoOutputStreamAssociation = new BufferedOutputStream(new FileOutputStream(daoFileAssociation));
                daoOutputStreamAssociation.write(javaTable.getClassAssociationDAOStr().getBytes());
                daoOutputStreamAssociation.flush();
                daoOutputStreamAssociation.close();
            }

            if (StringUtils.isNotBlank(javaTable.getClassAssociationServiceStr())) {
                serviceFileAssociation = new File(tmpPath + javaTable.getClassAssociationServicePath());
                serviceFileAssociation.createNewFile();
                serviceOutputStreamAssociation = new BufferedOutputStream(new FileOutputStream(serviceFileAssociation));
                serviceOutputStreamAssociation.write(javaTable.getClassAssociationServiceStr().getBytes());
                serviceOutputStreamAssociation.flush();
                serviceOutputStreamAssociation.close();
            }
            if (StringUtils.isNotBlank(javaTable.getClassAssociationServiceImplPath())) {
                serviceImplFileAssociation = new File(tmpPath + javaTable.getClassAssociationServiceImplPath());
                serviceImplFileAssociation.createNewFile();
                serviceImplOutputStreamAssociation = new BufferedOutputStream(new FileOutputStream(serviceImplFileAssociation));
                serviceImplOutputStreamAssociation.write(javaTable.getClassAssociationServiceImplStr().getBytes());
                serviceImplOutputStreamAssociation.flush();
                serviceImplOutputStreamAssociation.close();
            }
            if (StringUtils.isNotBlank(javaTable.getAssociationMapperStr())) {
                mapperFileAssociation = new File(tmpPath + javaTable.getAssociationMapperPath());
                mapperFileAssociation.createNewFile();
                mapperFileOutputStreamAssociation = new BufferedOutputStream(new FileOutputStream(mapperFileAssociation));
                mapperFileOutputStreamAssociation.write(javaTable.getAssociationMapperStr().getBytes());
                mapperFileOutputStreamAssociation.flush();
                mapperFileOutputStreamAssociation.close();
            }

        }
        File file = new File(tmpPath + zipFileName);
        OutputStream outputStream = new FileOutputStream(file);
        ZipUtils.toZip(tmpPath + dirPath, outputStream, true);
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = new FileInputStream(tmpPath + zipFileName);


        FileUtils.deleteDirectory(tmpPath + dirPath);
        file.delete();

        return inputStream;
    }
}