package org.example.boardprac.service;

import org.example.boardprac.exception.BoardNotFountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public String storeFile(MultipartFile file){
        if(file==null||file.isEmpty()){
            return null;
        }
        try{
            File dir = new File(uploadDir).getAbsoluteFile();
            if(!dir.exists()){
                dir.mkdirs();
            }

            String storedName = UUID.randomUUID()+"_"+file.getOriginalFilename();
            File dest = new File(dir,storedName);

            file.transferTo(dest);
            return dest.getPath();
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.",e);
        }
    }

    public Resource downloadFile(String fileName){
        File file = new File(new File(uploadDir).getAbsoluteFile(), fileName);
        try {
            Resource resource = new UrlResource(file.toURI());
            if(!resource.exists()){
                throw new BoardNotFountException("파일을 찾을 수 없습니다. fileName = "+fileName);
            }
            return resource;

        } catch (MalformedURLException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다."+e);
        }
    }

    public void deleteFile(String filePath){
        if(filePath==null||filePath.isBlank()){
            return;
        }
        File file = new File(filePath);
        if(!file.exists()){
            file.delete();
        }
    }
}
