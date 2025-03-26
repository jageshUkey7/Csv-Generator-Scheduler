package com.CsvGenerator.CsvGenratorServices;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CsvGenService {
	
	Logger log =LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	CsvGenRepository csvGenRepository;
	
	@Autowired
	FileProcessRepository fileProcessRepository;
	
	 @Value("${destinationLocation}")
	  private String destinationLocation;
	
	
	
	 @Scheduled(cron = "0 57 15 * * ?")
	public void GenerateCsv() {
		
		 log.info("scheduler for file generation started: " );
		 
	   List<DeliveryStatus> list = csvGenRepository.findLastDayData();
	    log.info("CSV file is being generated: " + list);
	    // Generate timestamp for the file name
	    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

	    String baseDestination = destinationLocation;
	    		//"C:/Users/LENOVO/Desktop/chatbot jk/";
	    		//"C:/Users/LENOVO/Desktop/UATWNP-13-11-24/CsvGenerator/src/main/resources/";
	    String fileName = "delivery_status_" + timestamp + ".csv";
	    String filePath = baseDestination + fileName;

	    // Configure CSVWriter to avoid double quotes
	    try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(filePath))
	            .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER) // No quotes on values
	            .build()) 
	    {
	        
	        // Add CSV Header
	        String[] header = {"ID", "CreatedOn", "DestinationNo", "EventDate", "ProcessorResponseId", "SourceNo", "Status","Cause","ProcessStatus;"};
	        writer.writeNext(header);

	        // Add Data Rows
	        for (DeliveryStatus status : list) {
	            String[] row = {
	                    String.valueOf(status.getId()),
	                    String.valueOf(status.getCreatedOn()),
	                    status.getDestinationNo(),
	                    String.valueOf(status.getEventDate()),
	                    status.getProcessorResponseId(),
	                    status.getSourceNo(),
	                    status.getStatus(),
	                    status.getCause(),
	                    String.valueOf(status.isProcessStatus())
	                    
	            };
	            writer.writeNext(row);
	        }

	        log.info("CSV file successfully generated at: " + filePath);

	        // Saving file details to repository
	        FileProcess fileProcess = new FileProcess();
	        fileProcess.setFileName(fileName);
	        fileProcess.setFilePath(filePath);

	        LocalDateTime dateTime = LocalDateTime.now();
	        fileProcess.setCreatedOn("" + dateTime);
	        fileProcess.setFlag(0);
	        fileProcessRepository.save(fileProcess);

	    } catch (IOException e) {
	    	log.info("Error while generating CSV file: " + e.getMessage());
	    }
	}
}
