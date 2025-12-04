package com.example.shoppingapp.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
// BU SƏTR VACİBDİR:
// Yalnız "features.user-export.enabled" parametri "true" olarsa bu Bean yaranacaq!
@ConditionalOnProperty(name = "features.user-export.enabled", havingValue = "true")
public class CsvUserExportService implements UserExportService {

    @Override
    public String exportUsersToCsv() {
        // Sadə CSV formatı simulyasiyası
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Username,Role\n");
        csv.append("1,Ali,User\n");
        csv.append("2,Vali,Admin\n");
        csv.append("3,Hacker,Banned\n");

        System.out.println(">>> CSV Export servisi işə düşdü!");
        return csv.toString();
    }
}