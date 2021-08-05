package com.oracle.example.mapper;

import com.oracle.example.module.BasicEntity;
import com.oracle.example.module.TestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface MapperDemo {

    MapperDemo demo = getMapper(MapperDemo.class);

    TestEntity query(BasicEntity entity);
}
