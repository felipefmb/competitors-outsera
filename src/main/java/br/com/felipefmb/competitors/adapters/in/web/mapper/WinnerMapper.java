package br.com.felipefmb.competitors.adapters.in.web.mapper;

import br.com.felipefmb.competitors.adapters.in.web.response.WinnersResponse;
import br.com.felipefmb.competitors.domain.model.Winners;

public class WinnerMapper {

    private WinnerMapper() {
    }

    public static WinnersResponse toData(Winners winners) {
        return new WinnersResponse(
                winners.min(),
                winners.max()
        );
    }

}
