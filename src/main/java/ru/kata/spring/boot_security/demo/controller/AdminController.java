package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /*@GetMapping("")
    public String getAllUsers(ModelMap modelMap) {
        modelMap.addAttribute("usersList", userService.findAll());

        return "users";
    }*/
    @GetMapping("")
    public String getAllUsers(ModelMap modelMap, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findUserByUsername(username);
        modelMap.addAttribute("usersList", userService.findAll());
        modelMap.addAttribute("currentUser", currentUser);

        return "users";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute(value = "user") User user) {
        userService.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/create")
    public String create(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("role", roleService.getAllUser());
        return "create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getById(id));
        modelMap.addAttribute("role", roleService.getAllUser());

        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute(value = "user") User user) {
        userService.update(user);

        return "redirect:/admin";
    }

    /*@PostMapping("users")
    public String delete(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }*/
    @PostMapping("users/delete")
    public String delete(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

   /* private void extracted(Model model) {
        model.addAttribute("usersList", userService.findAll());
    }
    @GetMapping(value = "/form/{id}")
    public String deleteUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        extracted(model);
        return "delete";
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }*/
}

