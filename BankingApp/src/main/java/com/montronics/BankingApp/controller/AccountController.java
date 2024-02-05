package com.montronics.BankingApp.controller;

import com.montronics.BankingApp.entity.Account;
import com.montronics.BankingApp.repository.AccountRepository;
import com.montronics.BankingApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;


    // obtener el listado
    @GetMapping("/")
    public String ListadoCuentas(Model modelo) {
        // String palabraClave ="Yeltsin";
        List<Account> cuentas = accountRepository.findAll();   // recupero el listado de las cuentas
        //List<Account> cuentas = accountRepository.findAll(palabraClave);   // recupero el listado de las cuentas
        modelo.addAttribute("cuentas", cuentas);
        return "index";
    }


    // creando cuenta
    @GetMapping("/nuevo")
    public String mostrarFormatoCuenta(Model modelo) {
        modelo.addAttribute("cuenta", new Account());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String crearCuenta(@Validated Account cuenta, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
        if(bindingResult.hasErrors()) {
            modelo.addAttribute("cuenta", cuenta);
            return "nuevo";
        }

        accountRepository.save(cuenta);
        redirect.addFlashAttribute("msgExito", "La cuenta ha sido creada");
        return "redirect:/";
    }


    // obtener cuenta por http
    @GetMapping("/cuentaid/{id}")
    public String mostrarCuenta(@PathVariable Long id, Model modelo) {
        // Obtener la cuenta por su ID usando el servicio
        Account cuenta = accountService.getAccount(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Agregar la cuenta al modelo con el nombre "cuenta"
        modelo.addAttribute("cuenta", cuenta);
        return "cuentaid";
    }

    //obtener cuenta por formulario
    @GetMapping("/cuentaid")
    public String mostrarFormatoId(Model modelo) {
        modelo.addAttribute("cuenta", new Account());
        return "ingreso";
    }

    // OBTENER CUENTA POR ID
    @PostMapping("/cuentaid")
    public String mostrarCuenta(@ModelAttribute Account cuentaid, Model modelo, RedirectAttributes flash) {
        String nombre = cuentaid.getNombreCuenta();



        //Optional<Account> optionalAccount = accountService.getAccountByNombre(nombre);// un solo resultado
        List<Account> accounts = accountService.getAccountByNombre(nombre);

        if (!accounts.isEmpty()) {
            //Account cuenta = accounts.get();
            modelo.addAttribute("cuentas", accounts);
            return "cuentaid";
        } else {
            flash.addFlashAttribute("error", "La cuenta con ID " + nombre + " no fue encontrada");
            return "redirect:/cuentaid";
        }
    }



}