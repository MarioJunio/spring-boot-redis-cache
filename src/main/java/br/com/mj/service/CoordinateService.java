package br.com.mj.service;

import br.com.mj.domain.entity.Coordinate;

import java.util.List;

public interface CoordinateService {

    Coordinate save(Coordinate coordinate);

    Coordinate update(Long id, Coordinate coordinate);

    void delete(Long id);

    List<Coordinate> findAll();

}
