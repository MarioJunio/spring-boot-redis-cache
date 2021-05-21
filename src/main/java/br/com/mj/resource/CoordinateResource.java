package br.com.mj.resource;

import br.com.mj.domain.entity.Coordinate;
import br.com.mj.service.CoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinates")
@RequiredArgsConstructor
public class CoordinateResource {

    private final CoordinateService coordinateService;

    @PostMapping
    public ResponseEntity<Coordinate> add(@RequestParam("lat") double latitude, @RequestParam("long") double longitude) {
        return ResponseEntity.created(null).body(coordinateService.save(Coordinate.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build()));
    }

    @GetMapping
    public ResponseEntity<List<Coordinate>> retrieveAll() {
        return ResponseEntity.ok(coordinateService.findAll());
    }
}
