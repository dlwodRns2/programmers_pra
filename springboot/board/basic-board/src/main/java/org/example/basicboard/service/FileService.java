package org.example.basicboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    //getBytes() + Files.write() 방식과 비교했을 때의 핵심 차이
    //(기존) byte[] bytes = file.getBytes(); file.write(...)
    //(기존) 메모리 : getBytes()는 파일 "전체"를 byte[]로 힙 메모리에 올린다 -> 큰 파일들을/동시 업로드 시, OOM(Out of Memory) 위험
    //반면 transferTo는 통째로 올리지 않고 옮기며, 같은 디스크면 복사가 아니라 이동방식이라 가볍고 빠름
    public String storeFile(MultipartFile file){
        if(file==null||file.isEmpty()){
            return null;
        }

        try{
            //절대 경로로 다뤄 실행 위치에 영향받지 않게 한다
            File dir = new File(uploadDir).getAbsoluteFile();

            if(!dir.exists()){
                dir.mkdirs();
            }

            String storedFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir,storedFilename);
            file.transferTo(dest);

            return dest.getPath();

        }catch (Exception e){
            throw new IllegalStateException("파일 저장에 실패했습니다.",e);
        }
    }

    public Resource downloadFile(String fileName){
        try{
            File baseUploadDir = new File(uploadDir).getAbsoluteFile();
            File file = new File(baseUploadDir, fileName);
            Resource resource = new UrlResource(file.toURI());

            if(!resource.exists() || !resource.isReadable()){
                throw new IOException("파일을 읽어오는데 실패했습니다.");
            }
            return resource;

        }catch (MalformedInputException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다 fileName : " + fileName, e);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filePath){
        if(filePath==null||filePath.isBlank()){
            return;
        }
        File file = new File(filePath);

        if(!file.exists()){
            return;
        }
        file.delete();
    }
}
