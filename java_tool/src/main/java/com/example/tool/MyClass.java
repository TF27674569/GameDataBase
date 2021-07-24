package com.example.tool;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MyClass {

    private static final String BASE_URL = "https://github.com/TF27674569/GameDataBase/tree/main/";

    public static void main(String[] args) throws Exception {
        File dir = new File("D:\\workspace\\database\\mrp_database");
        List<File> dirMrpFile = findDirMrpFile(dir);
        List<MrpInfo> mrpInfoList = file2MrpInfo(dirMrpFile, BASE_URL);
        String s = new Gson().toJson(mrpInfoList);
        File file = new File("D:\\workspace\\database\\mrp_database", "core.bin");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
        osw.write(s);
        osw.flush();
        System.out.println("----size:"+dirMrpFile.size()+"----");
    }


    private static List<MrpInfo> file2MrpInfo(List<File> dirMrpFile, String baseUrls) {
        List<MrpInfo> mrpInfoList = new ArrayList<>();
        for (File file : dirMrpFile) {
            MrpInfo mrpInfo = MrpUtils.readMrpInfo(file.getAbsolutePath());
            mrpInfo.setDownloadUrl(baseUrls + file.getAbsolutePath()
                    .replaceAll("D:\\\\workspace\\\\database\\\\", "")
                    .replaceAll("\\\\", "/"));
            mrpInfoList.add(mrpInfo);
        }

        return mrpInfoList;
    }

    /**
     * 获取文件夹下的mrp列表文件
     */
    private static List<File> findDirMrpFile(File file) {
        List<File> mrpFiles = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    mrpFiles.addAll(findDirMrpFile(childFile));
                }
            }
        } else if (file.getName().endsWith(".mrp")) {
            mrpFiles.add(file);
        }
        return mrpFiles;
    }
}