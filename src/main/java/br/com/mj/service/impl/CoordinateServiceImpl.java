package br.com.mj.service.impl;

import br.com.mj.constants.CacheNames;
import br.com.mj.domain.entity.Coordinate;
import br.com.mj.repository.CoordinateRepository;
import br.com.mj.service.CoordinateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoordinateServiceImpl implements CoordinateService {

    private final CoordinateRepository coordinateRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @CacheEvict(cacheNames = CacheNames.COORDINATE, allEntries = true)
    @Transactional
    @Override
    public Coordinate save(Coordinate coordinate) {
        log.info("M=save, coordinate={}", coordinate.toString());

        redisTemplate.opsForValue().increment("count::save");

        return coordinateRepository.save(coordinate);
    }

    @CachePut(cacheNames = CacheNames.COORDINATE, key = "#id")
    @Transactional
    @Override
    public Coordinate update(Long id, Coordinate coordinate) {
        log.info("M=update, id={} coordinate={}", id, coordinate.toString());

        redisTemplate.opsForValue().increment("count::update");

        coordinate.setId(id);

        return coordinateRepository.save(coordinate);
    }

    @CacheEvict(cacheNames = CacheNames.COORDINATE, key = "#id")
    @Transactional
    @Override
    public void delete(Long id) {
        log.info("M=delete, id={}", id);

        redisTemplate.opsForValue().increment("count::delete");

        coordinateRepository.deleteById(id);
    }

    @Cacheable(cacheNames = CacheNames.COORDINATE, key = "#root.method.name")
    @Override
    public List<Coordinate> findAll() {
        log.info("M=findAll, search all coordinates");

        redisTemplate.opsForValue().increment("count::findAll");

        return coordinateRepository.findAll();
    }
}
