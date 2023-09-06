package site.bitrun.cryptocurrency.global.api.upbit.service;

import site.bitrun.cryptocurrency.global.api.upbit.domain.UpbitMarket;
import site.bitrun.cryptocurrency.global.api.upbit.dto.UpbitMarketDto;

import java.util.List;

public interface UpbitService {

    void saveUpbitMarket();

    List<UpbitMarket> getUpbitMarketList();
}
