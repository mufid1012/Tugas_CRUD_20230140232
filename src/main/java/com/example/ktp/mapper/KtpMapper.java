package com.example.ktp.mapper;

import com.example.ktp.dto.KtpRequestDto;
import com.example.ktp.dto.KtpResponseDto;
import com.example.ktp.entity.Ktp;
import org.springframework.stereotype.Component;

@Component
public class KtpMapper {

    // Convert Entity to Response DTO
    public KtpResponseDto toResponseDto(Ktp ktp) {
        KtpResponseDto dto = new KtpResponseDto();
        dto.setId(ktp.getId());
        dto.setNomorKtp(ktp.getNomorKtp());
        dto.setNamaLengkap(ktp.getNamaLengkap());
        dto.setAlamat(ktp.getAlamat());
        dto.setTanggalLahir(ktp.getTanggalLahir());
        dto.setJenisKelamin(ktp.getJenisKelamin());
        return dto;
    }

    // Convert Request DTO to Entity
    public Ktp toEntity(KtpRequestDto dto) {
        Ktp ktp = new Ktp();
        ktp.setNomorKtp(dto.getNomorKtp());
        ktp.setNamaLengkap(dto.getNamaLengkap());
        ktp.setAlamat(dto.getAlamat());
        ktp.setTanggalLahir(dto.getTanggalLahir());
        ktp.setJenisKelamin(dto.getJenisKelamin());
        return ktp;
    }

    // Update existing Entity from Request DTO
    public void updateEntityFromDto(KtpRequestDto dto, Ktp ktp) {
        ktp.setNomorKtp(dto.getNomorKtp());
        ktp.setNamaLengkap(dto.getNamaLengkap());
        ktp.setAlamat(dto.getAlamat());
        ktp.setTanggalLahir(dto.getTanggalLahir());
        ktp.setJenisKelamin(dto.getJenisKelamin());
    }
}
