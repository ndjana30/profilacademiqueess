package com.ess.profiles.student.rest;

import com.ess.profiles.student.models.File;
import com.ess.profiles.student.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("add")
    public Object postFile(@RequestParam("file")MultipartFile file) throws IOException {
        File f = new File();
        System.out.println("Created empty file object");
        try {
            f.setData(file.getBytes());
            System.out.println("Data Set");
            System.out.println("Data size is: "+file.getBytes().length/1000 + "KB");
            f.setName(file.getOriginalFilename());
            System.out.println("Name Set");
            f.setExtension(file.getContentType());
            System.out.println("Extension set");
            fileRepository.save(f);
            return new ResponseEntity<>("File set and saved", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("add/many")
    public Object postManyFiles(@RequestParam("file")MultipartFile[] files) throws IOException {

        try {
            for(MultipartFile fp:files)
            {
                File f = new File();
                System.out.println("Created empty file object");
                f.setData(fp.getBytes());
                System.out.println("Data Set");
                System.out.println("Data size is: "+fp.getBytes().length/1000 + "KB");
                f.setName(fp.getOriginalFilename());
                System.out.println("Name Set");
                f.setExtension(fp.getContentType());
                System.out.println("Extension set");
                fileRepository.save(f);
            }
            return new ResponseEntity<>("Files set and saved", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("{identification}/get")
    public Object getFile(@PathVariable ("identification") String identification)
    {
        List<File> researchFiles = fileRepository.findAll().stream().filter(f->f.getName().contains(identification)).collect(Collectors.toList());
        return researchFiles.isEmpty() ? new ResponseEntity<>("No file found", HttpStatus.NO_CONTENT) : new ResponseEntity<>(researchFiles, HttpStatus.OK);
    }

    @GetMapping("all")
    public Object getAllFiles()
    {
        List<File> researchFiles = fileRepository.findAll();
        return new ResponseEntity<>(researchFiles, HttpStatus.OK);
    }
}
