package br.com.felipefmb.competitors.adapters.in.web.mapper;

import br.com.felipefmb.competitors.adapters.in.web.response.ProducersInfoResponse;
import br.com.felipefmb.competitors.domain.model.ProducersWinners;

public class ProducerInfoMapper {

    private ProducerInfoMapper() {
    }

    public static ProducersInfoResponse toData(ProducersWinners producersWinners) {
        return new ProducersInfoResponse(
                producersWinners.min(),
                producersWinners.max()
        );
    }

}
