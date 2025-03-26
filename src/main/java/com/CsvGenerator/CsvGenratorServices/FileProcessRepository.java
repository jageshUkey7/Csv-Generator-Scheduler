package com.CsvGenerator.CsvGenratorServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileProcessRepository extends JpaRepository<FileProcess, Long>{

}
