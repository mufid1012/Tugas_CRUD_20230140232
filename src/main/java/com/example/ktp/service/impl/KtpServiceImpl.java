package com.example.ktp.service.impl;

import com.example.ktp.dto.KtpRequestDto;
import com.example.ktp.dto.KtpResponseDto;
import com.example.ktp.entity.Ktp;
import com.example.ktp.exception.DuplicateResourceException;
import com.example.ktp.exception.ResourceNotFoundException;
import com.example.ktp.mapper.KtpMapper;
import com.example.ktp.repository.KtpRepository;
import com.example.ktp.service.KtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KtpServiceImpl implements KtpService {

    @Autowired
    private KtpRepository ktpRepository;

    @Autowired
    private KtpMapper ktpMapper;

    // Ambil semua data KTP
    @Override
    public List<KtpResponseDto> getAllKtp() {
        return ktpRepository.findAll()
                .stream()
                .map(ktpMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Ambil data KTP berdasarkan ID
    @Override
    public KtpResponseDto getKtpById(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data KTP dengan id " + id + " tidak ditemukan"));
        return ktpMapper.toResponseDto(ktp);
    }

    // Tambah data KTP baru
    @Override
    public KtpResponseDto createKtp(KtpRequestDto ktpRequestDto) {
        // Validasi duplikat nomor KTP
        if (ktpRepository.existsByNomorKtp(ktpRequestDto.getNomorKtp())) {
            throw new DuplicateResourceException("Nomor KTP " + ktpRequestDto.getNomorKtp() + " sudah terdaftar");
        }

        Ktp ktp = ktpMapper.toEntity(ktpRequestDto);
        Ktp savedKtp = ktpRepository.save(ktp);
        return ktpMapper.toResponseDto(savedKtp);
    }

    // Perbarui data KTP
    @Override
    public KtpResponseDto updateKtp(Integer id, KtpRequestDto ktpRequestDto) {
        Ktp existingKtp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data KTP dengan id " + id + " tidak ditemukan"));

        // Validasi duplikat nomor KTP (kecuali milik record yang sama)
        if (ktpRepository.existsByNomorKtpAndIdNot(ktpRequestDto.getNomorKtp(), id)) {
            throw new DuplicateResourceException("Nomor KTP " + ktpRequestDto.getNomorKtp() + " sudah terdaftar");
        }

        ktpMapper.updateEntityFromDto(ktpRequestDto, existingKtp);
        Ktp updatedKtp = ktpRepository.save(existingKtp);
        return ktpMapper.toResponseDto(updatedKtp);
    }

    // Hapus data KTP
    @Override
    public void deleteKtp(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data KTP dengan id " + id + " tidak ditemukan"));
        ktpRepository.delete(ktp);
    }
}
