package com.cauamattosprj.freeze.modules.finances.personal.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
    @Autowired
    private BalanceService service;

    @GetMapping()
    public ResponseEntity<Integer> getBalance() {
        System.out.println("CHAMOU GETBALANCE");
        return ResponseEntity.ok(service.getBalance());
    }
}
