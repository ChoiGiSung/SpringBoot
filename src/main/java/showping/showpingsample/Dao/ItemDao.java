package showping.showpingsample.Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import showping.showpingsample.Dto.ItemDto;
import showping.showpingsample.service.ItemRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDao implements ItemRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //상품등록
    @Override
    public ItemDto save(ItemDto itemDto) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("item").usingGeneratedKeyColumns("item_id");

        Map<String,Object> parameters=new HashMap<>();
        parameters.put("user_name",itemDto.getUser_name());
        parameters.put("item_name",itemDto.getItem_name());
        parameters.put("item_price",itemDto.getItem_price());
        parameters.put("item_mount",itemDto.getItem_mount());

        Number key=jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        itemDto.setItem_id(key.longValue());

        return itemDto;
    }
    //상품 리스트
    @Override
    public List<ItemDto> findAll() {
        return jdbcTemplate.query("select * from item",itemDtoRowMapper());
    }

    //반환할때
    private RowMapper<ItemDto> itemDtoRowMapper(){
        return new RowMapper<ItemDto>() {
            @Override
            public ItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ItemDto itemDto=new ItemDto();
                itemDto.setItem_id(rs.getLong("item_id"));
                itemDto.setItem_name(rs.getString("item_name"));
                itemDto.setUser_name(rs.getString("user_name"));
                itemDto.setItem_price(rs.getInt("item_price"));
                itemDto.setItem_mount(rs.getInt("item_mount"));
                return itemDto;
            }
        };
    }
}
