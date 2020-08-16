package showping.showpingsample.Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import showping.showpingsample.Dto.UserDto;
import showping.showpingsample.service.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDao implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDto save(UserDto userDto) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("user_id");

        Map<String,Object> parameters=new HashMap<>();
        parameters.put("user_name",userDto.getUser_name());
        parameters.put("city",userDto.getCity());
        parameters.put("tel",userDto.getTel());

        Number key=jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        userDto.setId(key.longValue());
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        return jdbcTemplate.query("select * from user",userDtoRowMapper());
    }

    @Override
    public Optional<UserDto> findByName(String name) {
        List<UserDto> result=jdbcTemplate.query("select * from user where user_name=?",userDtoRowMapper(),name);
        return result.stream().findAny();
    }

    private RowMapper<UserDto> userDtoRowMapper(){
     return new RowMapper<UserDto>() {
         @Override
         public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
             UserDto userDto=new UserDto();
             userDto.setId(rs.getLong("user_id"));
             userDto.setUser_name(rs.getString("user_name"));
             userDto.setCity(rs.getString("city"));
             userDto.setTel(rs.getInt("tel"));

             return userDto;
         }
     };
    }




}
