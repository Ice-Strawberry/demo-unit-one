package com.example.demo.dao;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyy
 * @since 2020-12-16
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
   List<User> getOne ();
}
