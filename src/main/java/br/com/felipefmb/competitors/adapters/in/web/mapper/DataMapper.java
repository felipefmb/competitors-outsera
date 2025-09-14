package br.com.felipefmb.competitors.adapters.in.web.mapper;

import br.com.felipefmb.competitors.adapters.in.web.response.DataResponse;

public class DataMapper {

    public static DataResponse toData(Object payload) {
        return new DataResponse(payload);
    }

}
