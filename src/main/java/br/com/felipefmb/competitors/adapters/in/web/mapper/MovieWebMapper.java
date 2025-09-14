package br.com.felipefmb.competitors.adapters.in.web.mapper;

import br.com.felipefmb.competitors.adapters.in.web.request.MovieRequest;
import br.com.felipefmb.competitors.domain.model.Movie;
import br.com.felipefmb.competitors.adapters.in.web.response.MovieResponse;

import java.util.List;

public final class MovieWebMapper {

    private MovieWebMapper() {
    }

    public static Movie toDomain(MovieRequest req) {
        if (req == null) return null;
        return new Movie(
                req.id(),
                req.year(), // id definido no core/persistência
                req.title(),
                req.studio(),
                req.producer(),
                req.winner()
        );
    }

    public static List<Movie> toDomain(List<MovieRequest> reqs) {
        return reqs.stream().map(MovieWebMapper::toDomain).toList();
    }


    // Domain -> Response (para devolver ao cliente)
    public static MovieResponse toResponse(Movie domain) {
        if (domain == null) return null;
        return new MovieResponse(
                domain.id(),
                domain.title(),
                domain.year(),
                domain.studio(),
                domain.producer(),
                domain.winner()
        );
    }

    public static List<MovieResponse> toResponse(List<Movie> domains) {
        return domains.stream().map(MovieWebMapper::toResponse).toList();
    }

}
