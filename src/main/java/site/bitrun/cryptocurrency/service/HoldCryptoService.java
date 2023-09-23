package site.bitrun.cryptocurrency.service;

import site.bitrun.cryptocurrency.domain.HoldCrypto;
import site.bitrun.cryptocurrency.dto.HoldCryptoDto;

import java.util.List;

public interface HoldCryptoService {

    void buyCrypto(long memberId, String marketCode, long buyKrw);

    List<HoldCryptoDto> getHoldCryptoList(long memberId);
}
