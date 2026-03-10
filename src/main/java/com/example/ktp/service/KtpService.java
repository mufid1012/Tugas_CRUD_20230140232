package com.example.ktp.service;

import com.example.ktp.dto.KtpRequestDto;
import com.example.ktp.dto.KtpResponseDto;

import java.util.List;

public interface KtpService {

    List<KtpResponseDto> getAllKtp();

    KtpResponseDto getKtpById(Integer id);

    KtpResponseDto createKtp(KtpRequestDto ktpRequestDto);

    KtpResponseDto updateKtp(Integer id, KtpRequestDto ktpRequestDto);

    void deleteKtp(Integer id);
}
