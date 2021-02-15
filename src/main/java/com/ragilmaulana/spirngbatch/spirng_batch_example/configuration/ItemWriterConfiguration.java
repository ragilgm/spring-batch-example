package com.ragilmaulana.spirngbatch.spirng_batch_example.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.PersonHistory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ItemWriterConfiguration {
	

	@Bean
	public ItemWriter<PersonHistory> itemWriter(NamedParameterJdbcTemplate template){
		final String query = "insert into person_history (id, fullname, phone_number, created_Date, status)"
				+ "value (:id,:fullname, :phoneNumber, :createdDate,'EFECTIVE' )";
		JdbcBatchItemWriter<PersonHistory> itemWriter = new JdbcBatchItemWriter<>() {
			@Override
			public void write(List<? extends PersonHistory> items) throws Exception{
				super.write(items);
				logger.info("item processed - "+items.size());
				delete(items.stream().map(PersonHistory::getId).collect(Collectors.toList()), template);
			}
		};
		itemWriter.setSql(query);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		itemWriter.setJdbcTemplate(template);
		itemWriter.setAssertUpdates(false);
		return itemWriter;
	}
	
	
	public void delete(final List<Integer> list, NamedParameterJdbcTemplate jdbcTemplate) {
		final String delete_query = "delete from person where id in (:id)";
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", list);
		jdbcTemplate.update(delete_query, parameterSource);
	}
}
