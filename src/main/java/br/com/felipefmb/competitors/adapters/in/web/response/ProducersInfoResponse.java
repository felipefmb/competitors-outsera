package br.com.felipefmb.competitors.adapters.in.web.response;

import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.ProducerInfo;

import java.util.List;

public record ProducersInfoResponse(
        List<ProducerInfo> min,
        List<ProducerInfo> max
) implements Response {
}
