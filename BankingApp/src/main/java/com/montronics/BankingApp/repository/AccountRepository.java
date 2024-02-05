package com.montronics.BankingApp.repository;

import com.montronics.BankingApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //buscar por nombre
    List<Account> findByNombreCuenta(String nombre);

    // PARA BUSCAR POR PALABRA CLAVE
        @Query("SELECT p FROM Account p WHERE p.nombreCuenta LIKE %?1%")
   public List<Account> findAll(String palabraClave);

}
