package com.montronics.BankingApp.service;

import com.montronics.BankingApp.entity.Account;
import com.montronics.BankingApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    // agregando opcion para buscar
    public List<Account> listAll(String palabraClave){
        if(palabraClave != null){
            return accountRepository.findAll(palabraClave);
        }
        return accountRepository.findAll();
    }

    public Optional<Account> getAccount(Long id){
        return accountRepository.findById(id);
    }

    // buscar por nombre
    public List<Account> getAccountByNombre(String nombre) {
        return accountRepository.findByNombreCuenta(nombre);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).get();
    }


    public Account deposit(Long id, double amount){
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

}
