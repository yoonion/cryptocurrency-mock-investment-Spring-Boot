package site.bitrun.cryptocurrency.service;

public interface HoldCryptoService {

    void buyCrypto(long memberId, String marketCode, long buyKrw);
}
