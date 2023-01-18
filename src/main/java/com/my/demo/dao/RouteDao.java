package com.my.demo.dao;

import com.my.demo.entity.Route;
import org.springframework.data.repository.CrudRepository;

public interface RouteDao extends CrudRepository<Route, Long> {
}
