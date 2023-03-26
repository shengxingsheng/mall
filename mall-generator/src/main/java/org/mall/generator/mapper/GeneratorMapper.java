package org.mall.generator.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sxs
 */
@Mapper
public interface GeneratorMapper {

    @Select("SELECT table_name FROM information_schema.`TABLES` WHERE table_type = 'BASE TABLE' AND table_schema = '${db}';")
    List<String> listTableNames(@Param("db") String db);
}