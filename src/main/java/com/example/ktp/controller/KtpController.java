package com.example.ktp.controller;

import com.example.ktp.dto.KtpRequestDto;
import com.example.ktp.dto.KtpResponseDto;
import com.example.ktp.service.KtpService;
import com.example.ktp.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@CrossOrigin(origins = "*")
public class KtpController {

    @Autowired
    private KtpService ktpService;

    // POST /ktp - Tambah data KTP baru
    @PostMapping
    public ResponseEntity<ApiResponse<KtpResponseDto>> createKtp(@Valid @RequestBody KtpRequestDto ktpRequestDto) {
        KtpResponseDto createdKtp = ktpService.createKtp(ktpRequestDto);
        ApiResponse<KtpResponseDto> response = ApiResponse.created("Data KTP berhasil ditambahkan", createdKtp);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /ktp - Ambil semua data KTP
    @GetMapping
    public ResponseEntity<ApiResponse<List<KtpResponseDto>>> getAllKtp() {
        List<KtpResponseDto> ktpList = ktpService.getAllKtp();
        ApiResponse<List<KtpResponseDto>> response = ApiResponse.success("Berhasil mengambil data KTP", ktpList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET /ktp/{id} - Ambil data KTP berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpResponseDto>> getKtpById(@PathVariable Integer id) {
        KtpResponseDto ktp = ktpService.getKtpById(id);
        ApiResponse<KtpResponseDto> response = ApiResponse.success("Berhasil mengambil data KTP", ktp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // PUT /ktp/{id} - Perbarui data KTP
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpResponseDto>> updateKtp(@PathVariable Integer id,
            @Valid @RequestBody KtpRequestDto ktpRequestDto) {
        KtpResponseDto updatedKtp = ktpService.updateKtp(id, ktpRequestDto);
        ApiResponse<KtpResponseDto> response = ApiResponse.success("Data KTP berhasil diperbarui", updatedKtp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE /ktp/{id} - Hapus data KTP
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteKtp(@PathVariable Integer id) {
        ktpService.deleteKtp(id);
        ApiResponse<Void> response = ApiResponse.success("Data KTP dengan id " + id + " berhasil dihapus", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
