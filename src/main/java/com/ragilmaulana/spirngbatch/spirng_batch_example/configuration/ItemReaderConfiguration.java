package com.ragilmaulana.spirngbatch.spirng_batch_example.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.Person;

@Configuration
public class ItemReaderConfiguration {

	@Bean
	public ItemReader<Person> itemReader(DataSource dataSource){
		JdbcPagingItemReader<Person> jdbcPagingItemReader = new JdbcPagingItemReader<>();
		jdbcPagingItemReader.setDataSource(dataSource);
		jdbcPagingItemReader.setPageSize(10000);
		
		PagingQueryProvider queryProvider = createQuery();
		jdbcPagingItemReader.setQueryProvider(queryProvider);
		jdbcPagingItemReader.setRowMapper(new BeanPropertyRowMapper<>(Person.class));
		return jdbcPagingItemReader;
	}
	
	
	private PagingQueryProvider createQuery() {
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("SELECT * ");
		queryProvider.setFromClause("from person");
		queryProvider.setSortKeys(sortByCreationDate());
		return queryProvider;
	}
	
	
	private Map<String, Order> sortByCreationDate(){
		Map<String, Order> orderMap = new LinkedHashMap<>();
		orderMap.put("created_date", Order.ASCENDING);
		return orderMap;
	}
}
