package todolist.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import todolist.demo.dto.TodoDto;
import todolist.demo.service.TodoService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TodoDao implements TodoService {


    JdbcTemplate jdbcTemplate;

    public TodoDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TodoDto> findAll() {

        return jdbcTemplate.query("select * from todo",rowMapper());
    }

    @Override
    public String join(TodoDto todoDto) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo");

        Map<String,Object> parameters=new HashMap<>();
        parameters.put("title",todoDto.getTitle());
        parameters.put("name",todoDto.getName());
        parameters.put("sequence",todoDto.getSequence());
        parameters.put("type",todoDto.getType());
        parameters.put("regdate",todoDto.getRegdate());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));


        return "Ok";
    }

    @Override
    public String godoing(Long id) {
        Map<String,Long> parameter=new HashMap<>();
        parameter.put("id",id);
        //map 사용이유눈?
        jdbcTemplate.update("update todo set type = 'doing' where id = ? ",id);
        return null;
    }

    @Override
    public String godone(Long id) {

        jdbcTemplate.update("update todo set type='done' where id=?",id);
        return null;
    }


    private RowMapper<TodoDto> rowMapper(){
        return new RowMapper<TodoDto>() {
            @Override
            public TodoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                TodoDto todoDto=new TodoDto();

                todoDto.setId(rs.getLong("id"));
                todoDto.setName(rs.getString("name"));
                todoDto.setTitle(rs.getString("title"));
                todoDto.setSequence(rs.getInt("sequence"));
                todoDto.setType(rs.getString("type"));
                todoDto.setRegdate(rs.getDate("regdate"));

                return todoDto;
            }
        };
    }
}
