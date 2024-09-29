package com.controllers.accounts;

import static com.utils.Constant.UPLOAD_DIRECTORY;

import java.io.File;
import java.io.IOException;

import com.models.UserModel;
import com.services.IUserServices;
import com.services.impl.UserServicesImpl;
import com.utils.Constant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "profile", urlPatterns = {"/profile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5,
maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return Constant.DEFAULT_FILENAME;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/accounts/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String currentUsername = (String) session.getAttribute("username");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        // Retrieve parameters
        String newUsername = req.getParameter("username");
        String newEmail = req.getParameter("email");
        String newFullname = req.getParameter("fullname");
        String currentPassword = req.getParameter("password");
        String newPassword = req.getParameter("newpassword");

        IUserServices services = new UserServicesImpl();
        UserModel currentUser = (UserModel) session.getAttribute("profile");

        String uploadPath = File.separator + UPLOAD_DIRECTORY; // Upload to a specific directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String lastUploadedFilePath = null; // Variable to hold the last uploaded file path

        try {
            for (Part part : req.getParts()) {
                String fileName = getFileName(part);
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName; // Ensure unique names
                String fullPath = uploadPath + File.separator + uniqueFileName;

                part.write(fullPath);
                lastUploadedFilePath = fullPath; // Update to the last uploaded file path
            }

            // Store only the last uploaded file path in the session
            if (lastUploadedFilePath != null) {
                session.setAttribute("upfile", lastUploadedFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }

        // Retrieve the last uploaded file path from the session
        String uploadedImages = (String) session.getAttribute("upfile");
        String updatedUsername = (newUsername != null && !newUsername.trim().isEmpty()) ? newUsername : currentUsername;
        String updatedEmail = (newEmail != null && !newEmail.trim().isEmpty()) ? newEmail : currentUser.getEmail();
        String updatedFullname = (newFullname != null && !newFullname.trim().isEmpty()) ? newFullname : currentUser.getFullname();
        String updatedPassword = (newPassword != null && !newPassword.trim().isEmpty()) ? newPassword : currentUser.getPassword(); // Hash if needed

        // Check if current user and password match
        if (currentUser != null && currentUser.getPassword().equals(currentPassword)) {
            // Prepare user data for update

            // Update user details in the database
            boolean updateSuccessful = services.updateUser(currentUsername, updatedUsername, updatedEmail, updatedPassword, updatedFullname, uploadedImages);

            if (updateSuccessful) {
                // Update session attributes if needed
                session.setAttribute("username", updatedUsername);
                currentUser.setUsername(updatedUsername); // Update username in UserModel
                currentUser.setEmail(updatedEmail); // Update email in UserModel
                currentUser.setFullname(updatedFullname); // Update fullname in UserModel
                currentUser.setImages(uploadedImages); // Assuming you have a method to set images

                resp.getWriter().write("<h3>Update successful!</h3>");
            } else {
                resp.getWriter().write("<h3>Update failed! Please try again.</h3>");
            }
        } else {
        	resp.getWriter().write("<h3>Update failed!! Please try again.</h3>");
            
        }
    }
}
