package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    // ✅ Get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Upload single file
    @PostMapping("/uploadUser")
    public User uploadUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("file") MultipartFile file) {

        try {
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs(); // ✅ FIXED
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String fullPath = uploadDir + fileName;

            file.transferTo(new File(fullPath));

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setFilePath("uploads/" + fileName); // ✅ FIXED

            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Upload failed");
        }
    }

    // ✅ Upload multiple files
    @PostMapping("/uploadMultiple")
    public List<User> uploadMultiple(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("files") MultipartFile[] files) {

        List<User> users = new ArrayList<>();

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (MultipartFile file : files) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String fullPath = uploadDir + fileName;

                file.transferTo(new File(fullPath));

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setFilePath("uploads/" + fileName);

                users.add(userRepository.save(user));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    // ✅ Download file
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        try {
            String path = System.getProperty("user.dir") + "/uploads/" + fileName;

            File file = new File(path);
            Resource resource = new UrlResource(file.toURI());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }

    // ✅ Get user by email
    @GetMapping("/{email:.+}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // ✅ Delete user + file
    @DeleteMapping("/delete/{email:.+}")
    public String deleteUserWithFile(@PathVariable String email) {

        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return "User not found";
        }

        try {
            String filePath = user.getFilePath();

            if (filePath != null) {
                String fullPath = System.getProperty("user.dir") + File.separator + filePath;
                File file = new File(fullPath);

                if (file.exists()) {
                    file.delete();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        userRepository.delete(user);

        return "User + File deleted";
    }

    
}