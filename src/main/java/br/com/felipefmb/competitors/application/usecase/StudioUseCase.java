package br.com.felipefmb.competitors.application.usecase;

import br.com.felipefmb.competitors.adapters.out.csv.dto.MovieCsvSourceDTO;
import br.com.felipefmb.competitors.application.usecase.service.ProducerService;
import br.com.felipefmb.competitors.application.usecase.service.StudioService;
import br.com.felipefmb.competitors.domain.model.Producer;
import br.com.felipefmb.competitors.domain.model.Studio;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class StudioUseCase {

    private final StudioService service;

    public StudioUseCase(StudioService service) {
        this.service = service;
    }

    public List<Studio> save(Collection<MovieCsvSourceDTO> movies) {
        var studios = movies.stream()
                .collect(Collectors.groupingBy(MovieCsvSourceDTO::studio))
                .entrySet()
                .stream()
                .flatMap(x -> Stream.of(x.getKey()))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(x -> !x.isBlank())
                .map(StringUtils::trim)
                .distinct()
                .map(name -> new Studio(null, name))
                .toList();

        return service.save(studios);
    }

    public Studio save(Studio studio) {
        return service.save(studio);
    }

    public List<Studio> findByName(String name) {
        var studios = service.findByName(name);
        if (Objects.isNull(studios) || studios.isEmpty()) {
            return Collections.emptyList();
        }
        return studios;
    }
}
