package com.example.demo.controller;

import com.example.demo.form.LoginForm;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/signin")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "signin";
    }

    @PostMapping("/admin/contacts")
    public String signin(LoginForm form, RedirectAttributes redirectAttributes) {
        String email = form.getEmail();
        String password = form.getPassword();
        String message = userService.authenticateUser(email, password);
        if (message.equals("Authenticated User")) {
            // ログイン成功時の処理
            redirectAttributes.addFlashAttribute("successMessage", "Logged in successfully!");
            return "redirect:/admin/contacts"; // ログイン後のリダイレクト先を設定してください
        } else {
            // ログイン失敗時の処理
            redirectAttributes.addFlashAttribute("errorMessage", message);
            return "redirect:/admin/signin";
        }
    }
}
