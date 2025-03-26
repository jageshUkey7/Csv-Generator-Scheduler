package com.CsvGenerator.CsvGenratorServices;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CsvGenRepository extends JpaRepository<DeliveryStatus, Long>{

	@Query(value = "SELECT * FROM `chatnpay-delivery-status`.delivery_status WHERE DATE(created_on) = CURDATE() - INTERVAL 1 DAY;",nativeQuery = true)
	List<DeliveryStatus> findLastDayData();

}
